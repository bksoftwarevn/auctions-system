package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.CommentDto;
import com.bksoftwarevn.auction.model.CommentItem;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.CommentEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class,AuctionMapper.class,ReplyMapper.class})
public interface CommentMapper {

    @Mapping(source = "entity.auction.id", target = "auctionId")
    @Mapping(source = "entity.user.id", target = "userId")
    CommentItem mappingEntityToItem(CommentEntity entity);

    List<CommentItem> mappingEntitiesToItems(List<CommentEntity> items);
}
