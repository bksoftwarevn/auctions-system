package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReplyRepository extends JpaRepository<ReplyEntity, String>, JpaSpecificationExecutor<ReplyEntity> {
}