package com.bksoftwarevn.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PaginationDTO<S> {
    private long total;

    private long page;

    private long size;

    private List<S> items;
}
