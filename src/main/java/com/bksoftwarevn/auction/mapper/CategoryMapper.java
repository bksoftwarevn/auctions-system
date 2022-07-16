package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.CategoryItem;
import com.bksoftwarevn.auction.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(uses = {GroupMapper.class})
public interface CategoryMapper {

    List<CategoryItem> mappingItems(List<CategoryEntity> entities);

    CategoryItem mappingEntityToItem(CategoryEntity entity);
}
