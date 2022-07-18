package com.bksoftwarevn.auction.persistence.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Order {
    private String field;
    private SortType sortType;
}
