package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.EmailBody;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;

public interface EmailService {
    void sendEmail(EmailBody emailPostRequestBody);

    EmailBody buildEmailRequestActiveUser(UserEntity userEntity);

    EmailBody buildEmailResetPassword(UserEntity userEntity, String otp);

    EmailBody buildEmailContactUs(String originalFilename, String encodedString, String phone, String email, String name, String content, String reportType);

    EmailBody buildEmailNotificationResetPassword(UserEntity userEntity);
}
