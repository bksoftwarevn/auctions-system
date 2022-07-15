package com.bksoftwarevn.auction.constant;

import org.springframework.messaging.MessagingException;

@FunctionalInterface
public interface EmailsConsumer {

    void acceptEmails(String[] strings) throws MessagingException, javax.mail.MessagingException;
}