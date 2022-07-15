package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AuditDto implements Serializable {
    private final String id;
    private final String status;
    private final Instant eventTime;
    private final String actorUserId;
    private final String eventCategory;
    private final String actorUsername;
    private final String eventAction;
    private final String eventDesc;
    private final String error;
    private final String metadata;
    private final String additional;
}
