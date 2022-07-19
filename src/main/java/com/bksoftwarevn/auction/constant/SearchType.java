package com.bksoftwarevn.auction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    START_PRICE(1),
    START_TIME(2),
    END_TIME(3),
    NAME(4);

    int type;

}
