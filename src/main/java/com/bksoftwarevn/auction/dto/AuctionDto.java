package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class AuctionDto implements Serializable {
    private final String id;
    private final GroupDto.CategoryDto category;
    private final String title;
    private final Instant startDate;
    private final Instant endDate;
    private final Instant createdDate;
    private final String createdBy;
    private final UserDto user;
    private final String decscriptions;
    private final String status;
    private final String reason;
    private final String additional;
}
