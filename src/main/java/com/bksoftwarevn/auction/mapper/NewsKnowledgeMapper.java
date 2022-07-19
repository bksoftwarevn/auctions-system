package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.NewsKnowledgeDto;
import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NewsKnowledgeMapper {
    NewsKnowledgeEntity newsKnowledgeDtoToNewsKnowledgeEntity(NewsKnowledgeDto newsKnowledgeDto);

    NewsKnowledgeDto newsKnowledgeEntityToNewsKnowledgeDto(NewsKnowledgeEntity newsKnowledgeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNewsKnowledgeEntityFromNewsKnowledgeDto(NewsKnowledgeDto newsKnowledgeDto, @MappingTarget NewsKnowledgeEntity newsKnowledgeEntity);
}
