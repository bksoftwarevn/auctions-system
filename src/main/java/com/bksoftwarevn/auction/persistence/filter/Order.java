package com.bksoftwarevn.auction.persistence.filter;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class Order {
    private String field;
    private SortType sortType;
}
