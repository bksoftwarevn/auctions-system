package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.persistence.entity.ConfirmationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ConfirmationMapper {
    ConfirmationDto mapper(ConfirmationEntity confirmationEntity);

    List<ConfirmationDto> mappers(List<ConfirmationEntity> confirmationEntities);

    ConfirmationEntity mapper(ConfirmationDto confirmationDto);

}
