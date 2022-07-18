package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductDto implements Serializable {
    private final String id;
    private final String name;
    private final String startPrice;
    private final BrandDto brand;
    private final String descriptions;
    private final String mainImage;
    private final String buyer;
    private final String series;
    private final String images;
    private final AuctionDto auction;
}
