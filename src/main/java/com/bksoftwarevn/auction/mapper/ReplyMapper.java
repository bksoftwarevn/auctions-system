package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.ReplyDto;
import com.bksoftwarevn.auction.persistence.entity.ReplyEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class})
public interface ReplyMapper {
    ReplyEntity replyDtoToReplyEntity(ReplyDto replyDto);

    ReplyDto replyEntityToReplyDto(ReplyEntity replyEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReplyEntityFromReplyDto(ReplyDto replyDto, @MappingTarget ReplyEntity replyEntity);
}
