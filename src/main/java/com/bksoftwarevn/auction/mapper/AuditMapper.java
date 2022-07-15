package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.AuditDto;
import com.bksoftwarevn.auction.persistence.entity.AuditEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuditMapper {
    AuditDto mapper(AuditEntity auditEntity);
    AuditEntity mapper(AuditDto auditDto);
    List<AuditDto> mappers(List<AuditEntity> auditEntities);
}
