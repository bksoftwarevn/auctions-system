package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.BidIdDto;
import com.bksoftwarevn.auction.persistence.entity.BidEntityId;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BidIdMapper {
    BidEntityId bidIdDtoToBidEntityId(BidIdDto bidIdDto);

    BidIdDto bidEntityIdToBidIdDto(BidEntityId bidEntityId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBidEntityIdFromBidIdDto(BidIdDto bidIdDto, @MappingTarget BidEntityId bidEntityId);
}
