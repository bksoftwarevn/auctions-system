package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewsKnowledgeDto implements Serializable {
    private final NewsKnowledgeIdDto id;
    private final NewsDto news;
    private final UserDto user;
    private final Boolean isRead;
}
