package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.dto.AuditDto;
import com.bksoftwarevn.auction.persistence.entity.AuditEntity;

public interface AuditService {

    AuditEntity audit(AuditDto auditDto);

}
