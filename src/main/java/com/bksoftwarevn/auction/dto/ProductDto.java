package com.bksoftwarevn.auction.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDto implements Serializable {
    private final String id;
    private final String name;
    private final BigDecimal startPrice;
    private final BrandDto brand;
    private final String descriptions;
    private final String mainImage;
    private final String images;
    private final AuctionDto auction;
    private final String series;
    private final String buyer;
    private final BigDecimal maxBid;
    private final Set<BidDto> bids;
}
