package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandRepository extends JpaRepository<BrandEntity, String>, JpaSpecificationExecutor<BrandEntity> {
}