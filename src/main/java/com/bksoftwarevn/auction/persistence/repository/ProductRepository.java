package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {
    List<ProductEntity> findAllByStartPrice(BigDecimal startPrice);

}