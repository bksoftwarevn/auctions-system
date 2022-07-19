package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.NewsKnowledgeIdDto;
import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntityId;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NewsKnowledgeIdMapper {
    NewsKnowledgeEntityId newsKnowledgeIdDtoToNewsKnowledgeEntityId(NewsKnowledgeIdDto newsKnowledgeIdDto);

    NewsKnowledgeIdDto newsKnowledgeEntityIdToNewsKnowledgeIdDto(NewsKnowledgeEntityId newsKnowledgeEntityId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNewsKnowledgeEntityIdFromNewsKnowledgeIdDto(NewsKnowledgeIdDto newsKnowledgeIdDto, @MappingTarget NewsKnowledgeEntityId newsKnowledgeEntityId);
}
