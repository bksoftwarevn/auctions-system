package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
public class NewsDto implements Serializable {
    private final String id;
    private final String title;
    private final String content;
    private final String descriptions;
    private final String status;
    private final Set<NewsKnowledgeIdDto> newsKnowledges;
    private final String createdBy;
    private final Instant createdDate;
    private final Instant updatedDate;
}
