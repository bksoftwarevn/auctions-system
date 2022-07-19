package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeAuctionEntityIdDto implements Serializable {
    private final String userId;
    private final String auctionId;
}
