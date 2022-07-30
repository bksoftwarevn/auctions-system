package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.AuditDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.AuditMapper;
import com.bksoftwarevn.auction.persistence.entity.AuditEntity;
import com.bksoftwarevn.auction.persistence.repository.AuditRepository;
import org.junit.jupiter.api.Assertions;
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

        AuditServiceImpl auditService = new AuditServiceImpl(auditRepository, auditMapper);

        when(auditMapper.mapper(auditDTO)).thenReturn(auditEntity);
        when(auditRepository.save(auditEntity)).thenReturn(auditEntity);

        AuditEntity actualResult = auditService.audit(auditDTO);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    public void give1_whenAudit_then() {
        AuditDto auditDTO = mock(AuditDto.class);
        AuditEntity auditEntity = mock(AuditEntity.class);

        AuditServiceImpl auditService = new AuditServiceImpl(auditRepository, auditMapper);

        when(auditMapper.mapper(auditDTO)).thenReturn(auditEntity);
        when(auditRepository.save(auditEntity)).thenThrow(new AucException());

        AuditEntity actualResult = auditService.audit(auditDTO);
        Assertions.assertNull(actualResult);
    }
}