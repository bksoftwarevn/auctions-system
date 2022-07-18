package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class CommentDto implements Serializable {
    private final String id;
    private final AuctionDto auction;
    private final String parentId;
    private final String content;
    private final Instant createdDate;
    private final Instant updatedDate;
    private final String status;
    private final String additional;
    private final UserDto user;
    private final Set<ReplyDto> replies;
}
