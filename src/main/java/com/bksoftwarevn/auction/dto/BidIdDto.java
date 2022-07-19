package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BidIdDto implements Serializable {
    private final String productId;
    private final String userId;
}
