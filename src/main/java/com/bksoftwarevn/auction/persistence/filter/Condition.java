package com.bksoftwarevn.auction.persistence.filter;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class Condition {
    private String field;
    private Operator operator;
    private Object value;
    private List<Object> values; //Used in case of IN operator
}
