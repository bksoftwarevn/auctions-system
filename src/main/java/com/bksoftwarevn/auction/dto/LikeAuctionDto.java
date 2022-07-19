package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeAuctionDto implements Serializable {
    private final LikeAuctionIdDto id;
    private final UserDto user;
    private final AuctionDto auction;
    private final Boolean isLiked;
}
