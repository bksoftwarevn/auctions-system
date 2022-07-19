package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.EmailBody;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;

public interface EmailService {
    void sendEmail(EmailBody emailPostRequestBody);

    EmailBody buildEmailRequestActiveUser(UserEntity userEntity);

    EmailBody buildEmailResetPassword(UserEntity userEntity, String otp);

    EmailBody buildEmailNotificationResetPassword(UserEntity userEntity);

    EmailBody buildEmailAcceptedBid(AcceptBidResponse response);
}
