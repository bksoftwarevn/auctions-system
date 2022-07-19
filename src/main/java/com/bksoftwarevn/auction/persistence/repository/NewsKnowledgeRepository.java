package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntity;
import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsKnowledgeRepository extends JpaRepository<NewsKnowledgeEntity, NewsKnowledgeEntityId>, JpaSpecificationExecutor<NewsKnowledgeEntity> {
}