package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeAuctionIdDto implements Serializable {
    private final String userId;
    private final String auctionId;
}
