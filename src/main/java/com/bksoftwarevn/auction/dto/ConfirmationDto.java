package com.bksoftwarevn.auction.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class ConfirmationDto implements Serializable {
    private final String id;
    private final String username;
    private final String action;
    private final String otp;
    private final String status;
    private final Instant expireDate;
    private final String data;
}
