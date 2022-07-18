package com.bksoftwarevn.auction.dto;

import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Order;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Builder
public class SearchDTO {
    @Valid
    @NotNull
    private List<Condition> conditions;

    private List<Order> orders;

    @Min(value = 0, message = "page can not less than 0")
    private long page;

    @Min(value = 0, message = "size can not less than 0")
    private long size;
}
