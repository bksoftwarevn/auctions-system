package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface AuctionRepository extends JpaRepository<AuctionEntity, String>, JpaSpecificationExecutor<AuctionEntity> {

    long deleteByIdAndUserIdAndStatus(String auctionId, String userId, String status);

    AuctionEntity findByIdAndUserId(String auctionId, String userId);
    Optional<AuctionEntity> findByIdAndStatus(String userId, String status);

}