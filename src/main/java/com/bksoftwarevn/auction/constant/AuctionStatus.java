package com.bksoftwarevn.auction.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuctionStatus {
    PENDING, REJECTED, WAITING, STARTING, STOPPED, DELIVERY, DELIVERED, DELIVERY_SUCCESS, DELIVERY_FAILED;
}
