package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.NewsMapper;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateNewsResponse;
import com.bksoftwarevn.auction.model.SearchNewsItem;
import com.bksoftwarevn.auction.model.SearchNewsRequest;
import com.bksoftwarevn.auction.model.SearchNewsResponse;
import com.bksoftwarevn.auction.model.UpdateNewsRequest;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Order;
import com.bksoftwarevn.auction.persistence.filter.SortType;
import com.bksoftwarevn.auction.persistence.repository.NewsKnowledgeRepository;
import com.bksoftwarevn.auction.persistence.repository.NewsRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.CommonQueryService;
import com.bksoftwarevn.auction.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final UserRepository userRepository;
    private final CommonQueryService commonQueryService;
    private final NewsKnowledgeRepository newsKnowledgeRepository;


    @Override
    public SearchNewsResponse search(SearchNewsRequest searchNewsRequest) {
        SearchNewsResponse response = new SearchNewsResponse().code(AucMessage.PULL_NEWS_FAILED.getCode()).message(AucMessage.PULL_NEWS_FAILED.getMessage());
        try {

            List<Order> orders = new ArrayList<>();
            Order order = Order.builder()
                    .field(NewsEntity_.CREATED_DATE)
                    .sortType(SortType.ASC).build();
            orders.add(order);
            List<Condition> conditions = new ArrayList<>();

            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchNewsRequest.getPage())
                    .size(searchNewsRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<NewsEntity> newsEntityPaginationDTO = commonQueryService.search(NewsEntity.class, NewsEntity.class, searchDTO, null);

            SearchNewsItem searchNewsItem = new SearchNewsItem();
            searchNewsItem.setNews(mapper.mappingEntitiesToItems(newsEntityPaginationDTO.getItems()));
            searchNewsItem.setTotal(newsEntityPaginationDTO.getTotal());
            response.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage())
                    .data(searchNewsItem);

        } catch (Exception ex) {
            log.error("[CommentServiceImpl.search] search comment have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateNewsResponse create(com.bksoftwarevn.auction.model.CreateNewsRequest createNewsRequest) {
        CreateNewsResponse response = new CreateNewsResponse().code(AucMessage.CREATE_NEWS_FAILED.getCode()).message(AucMessage.CREATE_NEWS_FAILED.getMessage());
        try {

            NewsEntity entity = new NewsEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setDescriptions(createNewsRequest.getDescriptions());
            entity.setDescriptions(createNewsRequest.getDescriptions());
            entity.setTitle(createNewsRequest.getTitle());
            entity.setContent(createNewsRequest.getContent());
            entity.setCreatedBy(userRepository.findById(SecurityUtils.getCurrentUserId()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())).getName());
            entity.setStatus(ActionStatus.CREATED.name());
            entity.setCreatedDate(Instant.now());

            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_NEWS_SUCCESS.getCode()).message(AucMessage.CREATE_NEWS_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[NewsServiceImpl.create] Exception when create news: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CommonResponse delete(String id) {
        CommonResponse response = new CommonResponse().code(AucMessage.DELETE_NEWS_FAILED.getCode()).message(AucMessage.DELETE_NEWS_FAILED.getMessage());
        try {
            NewsEntity entity = repository.findById(id).orElseThrow(() -> new AucException(AucMessage.NEWS_NOT_FOUND.getCode(), AucMessage.NEWS_NOT_FOUND.getMessage()));
            repository.delete(entity);
            response.code(AucMessage.DELETE_NEWS_SUCCESS.getCode()).message(AucMessage.DELETE_NEWS_SUCCESS.getMessage());
        } catch (Exception ex) {
            log.error("[NewsServiceImpl.delete] delete news [{}] exception: ", id, ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateNewsResponse update(UpdateNewsRequest updateNewsRequest) {
        CreateNewsResponse response = new CreateNewsResponse().code(AucMessage.UPDATE_NEWS_FAILED.getCode()).message(AucMessage.UPDATE_NEWS_FAILED.getMessage());
        try {
            NewsEntity entity = repository.findById(updateNewsRequest.getId()).orElseThrow(() -> new AucException(AucMessage.NEWS_NOT_FOUND.getCode(), AucMessage.NEWS_NOT_FOUND.getMessage()));
            entity.setDescriptions(updateNewsRequest.getDescriptions());
            entity.setDescriptions(updateNewsRequest.getDescriptions());
            entity.setTitle(updateNewsRequest.getTitle());
            entity.setContent(updateNewsRequest.getContent());
            entity.setCreatedDate(Instant.now());
            entity.setUpdatedDate(Instant.now());
            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.UPDATE_NEWS_SUCCESS.getCode()).message(AucMessage.UPDATE_NEWS_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[NewsServiceImpl.update] Update news [{}] exception: ", updateNewsRequest, ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CommonResponse read(String currentUserId, String newsId) {
        CommonResponse response = new CommonResponse().code(AucMessage.READ_NEWS_FAILED.getCode()).message(AucMessage.READ_NEWS_FAILED.getMessage());
        try {

            NewsEntity newsEntity = repository.findById(newsId).orElseThrow(() -> new AucException(AucMessage.NEWS_NOT_FOUND.getCode(), AucMessage.NEWS_NOT_FOUND.getMessage()));
            UserEntity userEntity = userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
            NewsKnowledgeEntityId entityId = new NewsKnowledgeEntityId();
            entityId.setNewsId(newsEntity.getId());
            entityId.setUserId(userEntity.getId());
            NewsKnowledgeEntity entity = new NewsKnowledgeEntity();
            entity.setId(entityId);
            entity.setUser(userEntity);
            entity.setNews(newsEntity);
            entity.setIsRead(true);

            entity = newsKnowledgeRepository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.code(AucMessage.READ_NEWS_SUCCESS.getCode()).message(AucMessage.READ_NEWS_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[NewsServiceImpl.read] Exception when read news: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CreateNewsResponse detail(String currentUserId, String id) {
        CreateNewsResponse response = new CreateNewsResponse().code(AucMessage.READ_NEWS_FAILED.getCode()).message(AucMessage.READ_NEWS_FAILED.getMessage());
        try {

            NewsEntity newsEntity = repository.findById(id).orElseThrow(() -> new AucException(AucMessage.NEWS_NOT_FOUND.getCode(), AucMessage.NEWS_NOT_FOUND.getMessage()));
            UserEntity userEntity = userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
            NewsKnowledgeEntityId entityId = new NewsKnowledgeEntityId();
            entityId.setNewsId(newsEntity.getId());
            entityId.setUserId(userEntity.getId());
            NewsKnowledgeEntity entity = newsKnowledgeRepository.findById(entityId).orElse(new NewsKnowledgeEntity());
            response.code(AucMessage.READ_NEWS_SUCCESS.getCode()).message(AucMessage.READ_NEWS_SUCCESS.getMessage())
                    .data(mapper.mappingEntityToItem(newsEntity).isRead(entity.getIsRead()));
        } catch (Exception ex) {
            log.error("[NewsServiceImpl.read] Exception when read news: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }
}
