package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.AuctionDto;
import com.bksoftwarevn.auction.model.AuctionItem;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = {CategoryMapper.class, UserMapper.class})
public interface AuctionMapper {

    @Mapping(source = "entity.createdDate", target = "createdDate")
    @Mapping(source = "entity.endDate", target = "endDate")
    @Mapping(source = "entity.startDate", target = "startDate")
    AuctionItem mappingEntityToItem(AuctionEntity entity);

    List<AuctionItem> mappings(List<AuctionEntity> items);

    AuctionEntity auctionEntityDtoToAuctionEntity(AuctionDto auctionEntityDto);

    AuctionDto auctionEntityToAuctionEntityDto(AuctionEntity auctionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAuctionEntityFromAuctionEntityDto(AuctionDto auctionEntityDto, @MappingTarget AuctionEntity auctionEntity);
}
