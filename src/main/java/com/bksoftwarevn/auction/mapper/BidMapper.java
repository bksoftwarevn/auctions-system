package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.BidItem;
import com.bksoftwarevn.auction.persistence.entity.BidEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BidMapper {

    @Mapping(source = "entity.user.id", target = "userId")
    @Mapping(source = "entity.product.id", target = "productId")
    BidItem mappingEntityToItem(BidEntity entity);

    List<BidItem> mappingEntitiesToItems(List<BidEntity> items);
}
