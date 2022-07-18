package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.QueryCommon;
import com.bksoftwarevn.auction.service.CommonQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.jpa.QueryHints;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

import static com.bksoftwarevn.auction.constant.AucConstant.LOAD_GRAPH_MODE;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommonQueryServiceImpl implements CommonQueryService {
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    /**
     * Find object by id
     */
    @Override
    public <T> T getById(String idValue, Class<T> clazz, String entityGraphName, String idColumnName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);

        // select by criteria
        query.select(root).where(cb.equal(root.get(idColumnName), idValue));

        // Load by graph
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        if (!ObjectUtils.isEmpty(entityGraphName)) {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);
            typedQuery.setHint(LOAD_GRAPH_MODE, entityGraph);
        } else {
            typedQuery.setHint(QueryHints.HINT_READONLY, true);
        }

        return typedQuery.getSingleResult();
    }


    /**
     * Find all data of all entity in the system with criteria (filter and sort)
     *
     * @return list object type @returnClazz
     */
    @Override
    public <S, T> PaginationDTO<S> search(Class<T> clazz, Class<S> returnClazz, SearchDTO dto, String entityGraphName) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(clazz);
        var root = criteria.from(clazz);

        // build predicate
        var predicates = QueryCommon.buildPredicate(dto.getConditions(), criteriaBuilder, root);

        // build order
        var orders = QueryCommon.buildOrderList(dto.getOrders(), criteriaBuilder, root);

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).orderBy(orders);

        var total = count(clazz, dto.getConditions());

        // Build type query
        var typedQuery = entityManager.createQuery(criteria)
                .setFirstResult((int) (dto.getPage() * dto.getSize()))
                .setMaxResults((int) dto.getSize());

        if (!ObjectUtils.isEmpty(entityGraphName)) {
            var entityGraph = entityManager.getEntityGraph(entityGraphName);
            typedQuery.setHint(LOAD_GRAPH_MODE, entityGraph);
        } else {
            typedQuery.setHint(QueryHints.HINT_READONLY, true);
        }

        var objects = typedQuery.getResultList();

        // Map data to DTO
        var items = mapList(objects, returnClazz);

        return PaginationDTO.<S>builder()
                .total(total)
                .items(items)
                .page(dto.getPage())
                .size(dto.getSize())
                .build();
    }

    /**
     * Find all data of all entity in the system with criteria (filter and sort)
     *
     * @return list object type @returnClazz
     */
    @Override
    public <S, T> List<S> getList(Class<T> clazz, Class<S> returnClazz, SearchDTO dto, String entityGraphName) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(clazz);
        var root = criteria.from(clazz);

        // build predicate
        var predicates = QueryCommon.buildPredicate(dto.getConditions(), criteriaBuilder, root);

        // build order
        var orders = QueryCommon.buildOrderList(dto.getOrders(), criteriaBuilder, root);

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).orderBy(orders);


        // Build type query
        var typedQuery = entityManager.createQuery(criteria)
                .setFirstResult((int) (dto.getPage() * dto.getSize()))
                .setMaxResults((int) dto.getSize());

        if (!ObjectUtils.isEmpty(entityGraphName)) {
            var entityGraph = entityManager.getEntityGraph(entityGraphName);
            typedQuery.setHint(LOAD_GRAPH_MODE, entityGraph);
        } else {
            typedQuery.setHint(QueryHints.HINT_READONLY, true);
        }

        var objects = typedQuery.getResultList();

        // Map data to DTO
        return mapList(objects, returnClazz);
    }

    @Override
    public <T> Long count(Class<T> clazz, List<Condition> conditions) {
        Long total = 0L;
        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Long.class);
            var root = criteriaQuery.from(clazz);

            // build predicate
            var predicates = QueryCommon.buildPredicate(conditions, criteriaBuilder, root);

            criteriaQuery.select(criteriaBuilder.count(root)).where(predicates.toArray(Predicate[]::new));
            total = entityManager.createQuery(criteriaQuery).getSingleResult();

        } catch (Exception ex) {
            log.error("[CommonQuery.count] Exception: {} ", ex.getMessage());
        }
        return total;
    }

    @Override
    public <S, T> List<S> mapList(List<T> sources, Class<S> targetClass) {
        return sources.stream().map(item -> modelMapper.map(item, targetClass)).collect(Collectors.toList());
    }
}
