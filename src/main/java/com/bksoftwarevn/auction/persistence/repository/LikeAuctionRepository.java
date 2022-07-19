package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.LikeAuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.LikeAuctionEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LikeAuctionRepository extends JpaRepository<LikeAuctionEntity, LikeAuctionEntityId>, JpaSpecificationExecutor<LikeAuctionEntity> {
}