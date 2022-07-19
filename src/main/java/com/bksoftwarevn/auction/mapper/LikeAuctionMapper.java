package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.AuctionLikedItem;
import com.bksoftwarevn.auction.model.LikeItem;
import com.bksoftwarevn.auction.persistence.entity.LikeAuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.LikeAuctionEntityId;
import com.bksoftwarevn.auction.dto.LikeAuctionIdDto;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CategoryMapper.class, AuctionMapper.class})
public interface LikeAuctionMapper {
    LikeAuctionEntityId likeAuctionIdDtoToLikeAuctionEntityId(LikeAuctionIdDto likeAuctionIdDto);

    LikeAuctionIdDto likeAuctionEntityIdToLikeAuctionIdDto(LikeAuctionEntityId likeAuctionEntityId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLikeAuctionEntityIdFromLikeAuctionIdDto(LikeAuctionIdDto likeAuctionIdDto, @MappingTarget LikeAuctionEntityId likeAuctionEntityId);

    @Mapping(source = "entity.auction.id", target = "auctionId")
    @Mapping(source = "entity.user.id", target = "userId")
    LikeItem mappingEntityToItem(LikeAuctionEntity entity);

    @Mapping(source = "entity.auction.id", target = "id")
    @Mapping(source = "entity.auction.title", target = "title")
    @Mapping(source = "entity.auction.startDate", target = "startDate")
    @Mapping(source = "entity.auction.endDate", target = "endDate")
    @Mapping(source = "entity.auction.createdDate", target = "createdDate")
    @Mapping(source = "entity.auction.status", target = "status")
    @Mapping(source = "entity.isLiked", target = "isLiked")
    @Mapping(source = "entity.auction.category", target = "category")
    AuctionLikedItem mappingAuctionToItem(LikeAuctionEntity entity);
}
