package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class GroupDto implements Serializable {
    private final String id;
    private final String name;
    private final String type;
    private final String desc;
    private final String additional;
}
