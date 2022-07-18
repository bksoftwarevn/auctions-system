package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.persistence.filter.Condition;

import java.util.List;

public interface CommonQueryService {
    <T> T getById(String idValue, Class<T> clazz, String entityGraphName, String idColumnName);

    <S, T> PaginationDTO<S> search(Class<T> clazz, Class<S> returnClazz, SearchDTO dto, String entityGraphName);

    <S, T> List<S> getList(Class<T> clazz, Class<S> returnClazz, SearchDTO dto, String entityGraphName);

    <T> Long count(Class<T> clazz, List<Condition> conditions);

    <S, T> List<S> mapList(List<T> sources, Class<S> targetClass);
}
