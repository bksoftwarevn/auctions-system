package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.BidEntity;
import com.bksoftwarevn.auction.persistence.entity.BidEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BidRepository extends JpaRepository<BidEntity, BidEntityId>, JpaSpecificationExecutor<BidEntity> {
}