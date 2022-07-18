package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class ReplyDto implements Serializable {
    private final String id;
    private final String content;
    private final Instant createdDate;
    private final Instant updatedDate;
    private final String status;
    private final UserDto user;
    private final CommentDto comment;
}
