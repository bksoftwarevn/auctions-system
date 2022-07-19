package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.ProductItem;
import com.bksoftwarevn.auction.persistence.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BrandMapper.class, AuctionMapper.class})
public interface ProductMapper {

    @Mapping(source = "productEntity.brand.id", target = "brandId")
    @Mapping(source = "productEntity.auction.id", target = "auctionId")
    @Mapping(source = "productEntity.images", target = "images")
    ProductItem productEntityToProductItem(ProductEntity productEntity);

}
