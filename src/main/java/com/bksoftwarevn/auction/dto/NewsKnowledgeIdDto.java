package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewsKnowledgeIdDto implements Serializable {
    private final String newsId;
    private final String userId;
}
