package com.bksoftwarevn.auction.dto;

import com.bksoftwarevn.auction.persistence.entity.BidEntityId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BidDto implements Serializable {
    private final BidEntityId id;
    private final ProductDto product;
    private final UserDto user;
    private final BigDecimal price;
    private final Instant createdDate;
    private final Instant updatedDate;
    private final String status;
    private final Integer total;
}
