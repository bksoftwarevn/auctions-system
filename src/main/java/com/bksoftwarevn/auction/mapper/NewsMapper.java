package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.model.NewsItem;
import com.bksoftwarevn.auction.persistence.entity.NewsEntity;
import org.mapstruct.*;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NewsMapper {

    NewsItem mappingEntityToItem(NewsEntity entity);

    List<NewsItem> mappingEntitiesToItems(List<NewsEntity> items);
}
