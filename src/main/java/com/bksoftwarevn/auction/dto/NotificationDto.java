package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class NotificationDto implements Serializable {
    private final String id;
    private final String actionType;
    private final String content;
    private final Instant createdDate;
    private final Instant updatedDate;
    private final String receiver;
    private final Boolean isRead;
    private final String reason;
    private final String eventId;
}
