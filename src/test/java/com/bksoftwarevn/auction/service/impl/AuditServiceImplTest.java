package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.AuditDto;
import com.bksoftwarevn.auction.mapper.AuditMapper;
import com.bksoftwarevn.auction.persistence.entity.AuditEntity;
import com.bksoftwarevn.auction.persistence.repository.AuditRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditServiceImplTest {
    private final AuditRepository auditRepository = mock(AuditRepository.class);
    private final AuditMapper auditMapper = mock(AuditMapper.class);

    @Test
    public void give_whenAudit_then() {
        AuditDto auditDTO = mock(AuditDto.class);
        AuditEntity auditEntity = mock(AuditEntity.class);

        when(auditMapper.mapper(auditDTO)).thenReturn(auditEntity);
        AuditServiceImpl auditService = new AuditServiceImpl(auditRepository, auditMapper);

        auditService.audit(auditDTO);
    }
}