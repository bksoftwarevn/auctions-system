package com.bksoftwarevn.auction.persistence.specification;


import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public final class AuctionSpecification {

    public static Specification<AuctionEntity> hasStatusIn(Collection<String> statuses){
        return ((root, query, criteriaBuilder) -> root.get(AuctionEntity_.status).in(statuses));
    }

}
