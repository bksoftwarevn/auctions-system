package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.mapper.AuditMapper;
import com.bksoftwarevn.auction.dto.AuditDto;
import com.bksoftwarevn.auction.persistence.entity.AuditEntity;
import com.bksoftwarevn.auction.persistence.repository.AuditRepository;
import com.bksoftwarevn.auction.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public AuditEntity audit(AuditDto auditDto) {
        AuditEntity auditEntity = null;
        try {
            auditEntity = auditRepository.save(auditMapper.mapper(auditDto));
        } catch (Exception e) {
            log.error("[AuditServiceImpl.audit] Create audit exception: ", e);
        }
        return auditEntity;
    }
}
