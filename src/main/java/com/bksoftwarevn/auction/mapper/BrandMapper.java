package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.BrandDto;
import com.bksoftwarevn.auction.model.BrandItem;
import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BrandMapper {
    BrandEntity brandDtoToBrandEntity(BrandDto brandDto);

    BrandDto brandEntityToBrandDto(BrandEntity brandEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrandEntityFromBrandDto(BrandDto brandDto, @MappingTarget BrandEntity brandEntity);

    List<BrandItem> mappingItem(List<BrandEntity> entities);

    BrandItem mappingEntityToItem(BrandEntity entity);
}
