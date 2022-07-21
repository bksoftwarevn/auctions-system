package com.bksoftwarevn.auction.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuctionStatus {
    PENDING(1), REJECTED(2), WAITING(3), STARTING(4), STOPPED(5), DELIVERY(6), DELIVERED(7), DELIVERY_SUCCESS(9), DELIVERY_FAILED(8);

    int status;
}
