package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.CommentDto;
import com.bksoftwarevn.auction.model.CommentItem;
import com.bksoftwarevn.auction.persistence.entity.CommentEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class,AuctionMapper.class,ReplyMapper.class})
public interface CommentMapper {
    CommentEntity commentDtoToCommentEntity(CommentDto commentDto);

    CommentDto commentEntityToCommentDto(CommentEntity commentEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentEntityFromCommentDto(CommentDto commentDto, @MappingTarget CommentEntity commentEntity);

    CommentItem mappingEntityToItem(CommentEntity entity);
}
