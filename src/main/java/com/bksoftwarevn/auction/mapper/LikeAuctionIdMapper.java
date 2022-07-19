package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.LikeAuctionEntityIdDto;
import com.bksoftwarevn.auction.persistence.entity.LikeAuctionEntityId;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LikeAuctionIdMapper {
    LikeAuctionEntityId likeAuctionEntityIdDtoToLikeAuctionEntityId(LikeAuctionEntityIdDto likeAuctionEntityIdDto);

    LikeAuctionEntityIdDto likeAuctionEntityIdToLikeAuctionEntityIdDto(LikeAuctionEntityId likeAuctionEntityId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLikeAuctionEntityIdFromLikeAuctionEntityIdDto(LikeAuctionEntityIdDto likeAuctionEntityIdDto, @MappingTarget LikeAuctionEntityId likeAuctionEntityId);
}
