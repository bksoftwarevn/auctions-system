package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.GroupItem;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupItem mappingEntityToItem(GroupEntity entity);

    List<GroupItem> mappingItems(List<GroupEntity> roleEntities);
}
