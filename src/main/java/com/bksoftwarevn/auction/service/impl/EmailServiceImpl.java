package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.config.DefaultMailSenderProperties;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.EmailPriority;
import com.bksoftwarevn.auction.constant.EmailsConsumer;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.Attachment;
import com.bksoftwarevn.auction.model.EmailBody;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.service.EmailService;
import com.bksoftwarevn.auction.util.EmailUtil;
import com.bksoftwarevn.auction.util.ResourceMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final DefaultMailSenderProperties emailConfiguration;
    private final EmailUtil emailUtil;
    private static final boolean MULTIPART_MODE = true;


    @Override
    public void sendEmail(EmailBody emailPostRequestBody) {

        MimeMessage mimeMessage;

        try {
            if (emailConfiguration.isEnable()) {
                mimeMessage = convertToMimeMessage(emailPostRequestBody);
                mailSender.send(mimeMessage);
            } else {
                log.info("[EmailServiceImpl.sendEmail] Email service disabled!");
            }
        } catch (org.springframework.messaging.MessagingException | UnsupportedEncodingException | javax.mail.MessagingException e) {
            log.error("[EmailServiceImpl.sendEmail] Failed to convert request body to email From: {}, To: {}, Subject: {}", emailPostRequestBody.getFrom(), emailPostRequestBody.getTo(), emailPostRequestBody.getSubject());
        } catch (MailException ex) {
            log.error("Failed to send email From: {}, To: {}, Subject: {}", emailPostRequestBody.getFrom(), emailPostRequestBody.getTo(), emailPostRequestBody.getSubject(), ex);
        }
    }


    private MimeMessage convertToMimeMessage(EmailBody emailPostRequestBody)
            throws org.springframework.messaging.MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {
        final MimeMailMessage mimeMailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        final MimeMessage mimeMessage = mimeMailMessage.getMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE);

        setEmailAddresses(messageHelper::setTo, emailPostRequestBody.getTo());
        setEmailAddresses(messageHelper::setCc, emailPostRequestBody.getCc());
        setEmailAddresses(messageHelper::setBcc, emailPostRequestBody.getBcc());
        messageHelper.setText(decodeBase64(emailPostRequestBody.getBody()), emailPostRequestBody.isHtml());
        messageHelper.setPriority(EmailPriority.getPriority(emailPostRequestBody.isImportant()));
        messageHelper.setSubject(emailPostRequestBody.getSubject());
        setFromAddress(emailPostRequestBody.getFrom(), messageHelper);

        setAttachments(messageHelper, emailPostRequestBody.getAttachments());

        return mimeMessage;
    }

    private String decodeBase64(String base64String) {
        return new String(Base64.decodeBase64(base64String));
    }

    private void setFromAddress(String from, MimeMessageHelper messageHelper)
            throws org.springframework.messaging.MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {
        if (nonNull(from)) {
            messageHelper.setFrom(from);
        } else {
            messageHelper.setFrom(emailConfiguration.getFromAddress(), emailConfiguration.getFromName());
        }
    }

    private void setEmailAddresses(EmailsConsumer emailsConsumer, List<String> emails) throws org.springframework.messaging.MessagingException, javax.mail.MessagingException {
        if (!CollectionUtils.isEmpty(emails)) {
            emailsConsumer.acceptEmails(emails.toArray(new String[0]));
        }
    }

    private void setAttachments(MimeMessageHelper mimeMessageHelper, List<Attachment> attachments) {
        if (!CollectionUtils.isEmpty(attachments)) {
            attachments.stream().parallel().forEach(addAttachmentToEmail(mimeMessageHelper));
        }
    }

    private Consumer<Attachment> addAttachmentToEmail(MimeMessageHelper mimeMessageHelper) {
        return attachment -> {
            try {
                mimeMessageHelper.addAttachment(attachment.getFileName(),
                        () -> getAttachmentAsInputStream(attachment.getContent()));

            } catch (org.springframework.messaging.MessagingException | javax.mail.MessagingException e) {
                log.info("Failed to add file as attachment {}", attachment.getFileName());
                throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), e.getCause(), Boolean.TRUE);
            }
        };
    }

    private ByteArrayInputStream getAttachmentAsInputStream(String attachmentContent) {
        final byte[] bytes = Base64.decodeBase64(attachmentContent);
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public EmailBody buildEmailRequestActiveUser(UserEntity userEntity) {
        Map<String, Object> activeContextModel = new HashMap<>();
        activeContextModel.put("name", userEntity.getName());
        activeContextModel.put("key", userEntity.getActiveKey());

        return EmailBody.builder()
                .subject(ResourceMessageUtil.getMessage("mail.active.user.subject", userEntity.getLang()))
                .to(Collections.singletonList(userEntity.getEmail()))
                .body(emailUtil.generateHTMLContent("active-user", activeContextModel, new Locale(userEntity.getLang())))
                .html(true)
                .build();
    }

    @Override
    public EmailBody buildEmailResetPassword(UserEntity userEntity, String otp) {
        Map<String, Object> activeContextModel = new HashMap<>();
        activeContextModel.put("name", userEntity.getName());
        activeContextModel.put("otp", otp);

        return EmailBody.builder()
                .subject(ResourceMessageUtil.getMessage("mail.reset.pass.subject", userEntity.getLang()))
                .to(Collections.singletonList(userEntity.getEmail()))
                .body(emailUtil.generateHTMLContent("reset-password", activeContextModel, new Locale(userEntity.getLang())))
                .html(true)
                .build();
    }

    @Override
    public EmailBody buildEmailNotificationResetPassword(UserEntity userEntity) {
        Map<String, Object> activeContextModel = new HashMap<>();
        activeContextModel.put("name", userEntity.getName());

        return EmailBody.builder()
                .subject(ResourceMessageUtil.getMessage("mail.reset.pass.notification.subject", userEntity.getLang()))
                .to(Collections.singletonList(userEntity.getEmail()))
                .body(emailUtil.generateHTMLContent("reset-password-notification", activeContextModel, new Locale(userEntity.getLang())))
                .html(true)
                .build();
    }

    @Override
    public EmailBody buildEmailAcceptedBid(AcceptBidResponse response) {
        Map<String, Object> activeContextModel = new HashMap<>();
        activeContextModel.put("productName", response.getData().getProduct().getName());
        activeContextModel.put("productId", response.getData().getProduct().getId());
        activeContextModel.put("series", response.getData().getProduct().getSeries());
        activeContextModel.put("maxBid", response.getData().getProduct().getMaxBid());
        activeContextModel.put("buyerName", response.getData().getProduct().getBuyer().getName());
        activeContextModel.put("sellerName", response.getData().getProduct().getSeller().getName());
        activeContextModel.put("auctionId", response.getData().getAuction().getId());
        activeContextModel.put("auctionStart", response.getData().getAuction().getStartDate());
        activeContextModel.put("auctionEnd", response.getData().getAuction().getEndDate());
        activeContextModel.put("description", response.getData().getProduct().getAcceptedInfo());
        activeContextModel.put("valueDate", ResourceMessageUtil.getMessage("mail.accepted.bid.time", response.getData().getProduct().getBuyer().getLang()));

        return EmailBody.builder()
                .subject(ResourceMessageUtil.getMessage("mail.accepted.bid.subject", response.getData().getProduct().getBuyer().getLang()))
                .to(Collections.singletonList(response.getData().getProduct().getBuyer().getEmail()))
                .body(emailUtil.generateHTMLContent("accept-bid", activeContextModel, new Locale(response.getData().getProduct().getBuyer().getLang())))
                .html(true)
                .build();
    }
}
