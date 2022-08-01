package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.ActionCategory;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.mapper.NotificationMapper;
import com.bksoftwarevn.auction.mapper.ProductMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.NotificationEntity;
import com.bksoftwarevn.auction.persistence.entity.NotificationEntity_;
import com.bksoftwarevn.auction.persistence.entity.ProductEntity;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Operator;
import com.bksoftwarevn.auction.persistence.filter.Order;
import com.bksoftwarevn.auction.persistence.filter.SortType;
import com.bksoftwarevn.auction.persistence.repository.NotificationRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.CommonQueryService;
import com.bksoftwarevn.auction.service.NotificationsService;
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
public class NotificationsServiceImpl implements NotificationsService {
    private final NotificationRepository repository;
    private final NotificationMapper mapper;
    private final UserRepository userRepository;
    private final CommonQueryService commonQueryService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuctionMapper auctionMapper;
    private final CategoryMapper categoryMapper;


    @Override
    public SearchNotificationsResponse search(SearchNotificationsRequest searchNotificationsRequest) {
        SearchNotificationsResponse response = new SearchNotificationsResponse().code(AucMessage.PULL_NOTIFICATIONS_FAILED.getCode()).message(AucMessage.PULL_NOTIFICATIONS_FAILED.getMessage());
        try {
            com.bksoftwarevn.auction.model.SearchNotificationsItem searchNotificationsItem = new SearchNotificationsItem();
            UserEntity userRequest = userRepository.findById(SecurityUtils.getCurrentUserId()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));

            List<Order> orders = new ArrayList<>();
            Order order = Order.builder()
                    .field(NotificationEntity_.CREATED_DATE)
                    .sortType(SortType.DESC).build();
            orders.add(order);
            List<Condition> conditions = new ArrayList<>();


            if (userRequest.getRoles().stream().anyMatch(roleEntity -> roleEntity.getRole().equalsIgnoreCase("ROLE_ADMIN"))) {
                Condition condition = Condition.builder()
                        .field(NotificationEntity_.USER)
                        .operator(Operator.EQUALS)
                        .value(userRequest)
                        .build();
                conditions.add(condition);
            } else {
                Condition condition = Condition.builder()
                        .field(NotificationEntity_.RECEIVER)
                        .operator(Operator.EQUALS)
                        .value(userRequest.getUsername())
                        .build();
                conditions.add(condition);
                Condition deleted = Condition.builder()
                        .field(NotificationEntity_.IS_DELETED)
                        .operator(Operator.EQUALS)
                        .value(false)
                        .build();
                conditions.add(deleted);

            }

            Condition read = null;
            if (searchNotificationsRequest.getIsRead() != null) {
                read = Condition.builder()
                        .field(NotificationEntity_.IS_READ)
                        .operator(Operator.EQUALS)
                        .value(searchNotificationsRequest.getIsRead())
                        .build();
                conditions.add(read);
            }


            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchNotificationsRequest.getPage())
                    .size(searchNotificationsRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();


            PaginationDTO<NotificationEntity> notificationsEntityPaginationDTO = commonQueryService.search(NotificationEntity.class, NotificationEntity.class, searchDTO, null);

            if (Boolean.FALSE.equals(searchNotificationsRequest.getIsRead())) {
                searchNotificationsItem.setTotalUnRead(notificationsEntityPaginationDTO.getTotal());
            } else {
                if (ObjectUtils.isNotEmpty(read)) {
                    conditions.remove(read);
                }
                read = Condition.builder()
                        .field(NotificationEntity_.IS_READ)
                        .operator(Operator.EQUALS)
                        .value(false)
                        .build();
                conditions.add(read);
                searchNotificationsItem.setTotalUnRead(commonQueryService.count(NotificationEntity.class, conditions));
            }

            searchNotificationsItem.setTotal(notificationsEntityPaginationDTO.getTotal());
            searchNotificationsItem.setNotifications(mapper.mappingEntitiesToItems(notificationsEntityPaginationDTO.getItems()));
            response.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage())
                    .setData(searchNotificationsItem);

        } catch (Exception ex) {
            log.error("[NotificationServiceImpl.search] search noti have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CommonResponse create(CreateNotificationsRequest createNotificationsRequest) {
        CommonResponse response = new CommonResponse().code(AucMessage.CREATE_NOTIFICATIONS_FAILED.getCode()).message(AucMessage.CREATE_NOTIFICATIONS_FAILED.getMessage());
        try {

            NotificationEntity entity = new NotificationEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedDate(Instant.now());
            entity.setIsRead(false);
            entity.setUser(userRepository.findById(SecurityUtils.getCurrentUserId()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())));
            entity.setActionType(createNotificationsRequest.getActionType());
            entity.setActionCategory(createNotificationsRequest.getActionCategory());
            entity.setReason(createNotificationsRequest.getReason());
            entity.setReceiver(userRepository.findById(createNotificationsRequest.getReceiver()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())).getUsername());
            entity.setEventId(createNotificationsRequest.getEventId());
            entity.setContent(createNotificationsRequest.getContent());
            entity.setIsDeleted(false);

            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.code(AucMessage.CREATE_NOTIFICATIONS_SUCCESS.getCode()).message(AucMessage.CREATE_NOTIFICATIONS_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[NotificationsServiceImpl.create] Exception when create notification: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CommonResponse delete(String currentUserId, String id) {
        CommonResponse response = new CommonResponse().code(AucMessage.DELETE_NOTIFICATIONS_FAILED.getCode()).message(AucMessage.DELETE_NOTIFICATIONS_FAILED.getMessage());
        try {
            NotificationEntity entity = repository.findById(id).orElseThrow(() -> new AucException(AucMessage.NOTIFICATIONS_NOT_FOUND.getCode(), AucMessage.NOTIFICATIONS_NOT_FOUND.getMessage()));
            entity.setIsDeleted(true);
            repository.save(entity);
            response.code(AucMessage.DELETE_NOTIFICATIONS_SUCCESS.getCode()).message(AucMessage.DELETE_NOTIFICATIONS_SUCCESS.getMessage());
        } catch (Exception ex) {
            log.error("[NotificationsServiceImpl.delete] delete notifications [{}] exception: ", id, ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CommonResponse update(UpdateNotificationsRequest updateNotificationsRequest) {
        CommonResponse response = new CommonResponse().code(AucMessage.UPDATE_NOTIFICATIONS_FAILED.getCode()).message(AucMessage.UPDATE_NOTIFICATIONS_FAILED.getMessage());
        try {
            NotificationEntity entity = repository.findById(updateNotificationsRequest.getId()).orElseThrow(() -> new AucException(AucMessage.NOTIFICATIONS_NOT_FOUND.getCode(), AucMessage.NOTIFICATIONS_NOT_FOUND.getMessage()));
            if (!userRepository.findById(SecurityUtils.getCurrentUserId()).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())).equals(entity.getUser())) {
                throw new AucException(AucMessage.FORBIDDEN.getCode(), AucMessage.FORBIDDEN.getMessage());
            }
            entity.setActionType(updateNotificationsRequest.getActionType());
            entity.setActionCategory(updateNotificationsRequest.getActionCategory());
            entity.setReason(updateNotificationsRequest.getReason());
            entity.setReceiver(userRepository.findByUsername(updateNotificationsRequest.getReceiver()).getUsername());
            entity.setEventId(updateNotificationsRequest.getEventId());
            entity.setContent(updateNotificationsRequest.getContent());
            entity.setUpdatedDate(Instant.now());
            repository.save(entity);
            response.code(AucMessage.UPDATE_NOTIFICATIONS_SUCCESS.getCode()).message(AucMessage.UPDATE_NOTIFICATIONS_SUCCESS.getMessage());
        } catch (Exception ex) {
            log.error("[NotificationsServiceImpl.update] Update notifications [{}] exception: ", updateNotificationsRequest, ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public DetailNotificationsResponse detail(String currentUserId, String id) {
        DetailNotificationsResponse detailNotificationsResponse = new DetailNotificationsResponse().code(AucMessage.PULL_NOTIFICATIONS_FAILED.getCode()).message(AucMessage.PULL_NOTIFICATIONS_FAILED.getMessage());
        try {
            NotificationEntity notificationEntity = repository.findById(id).orElseThrow(() -> new AucException(AucMessage.NOTIFICATIONS_NOT_FOUND.getCode(), AucMessage.NOTIFICATIONS_NOT_FOUND.getMessage()));

            DetailNotificationsItem detailNotificationsItem = new DetailNotificationsItem();
            detailNotificationsItem.setActionType(notificationEntity.getActionType());
            detailNotificationsItem.setIsRead(notificationEntity.getIsRead());
            detailNotificationsItem.setActionCategory(notificationEntity.getActionCategory());


            if (ActionCategory.BID.name().equalsIgnoreCase(notificationEntity.getActionCategory())) {
                ProductEntity productEntity = productRepository.findById(notificationEntity.getEventId()).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));
                detailNotificationsItem.setProduct(productMapper.productEntityToProductItem(productEntity));
                detailNotificationsItem.setAuctions(auctionMapper.mappingEntityToItem(productEntity.getAuction()));
                detailNotificationsItem.setCategory(categoryMapper.mappingEntityToItem(productEntity.getAuction().getCategory()));
            }

            detailNotificationsResponse.message(AucMessage.PULL_NOTIFICATIONS_SUCCESS.getMessage()).code(AucMessage.PULL_NOTIFICATIONS_SUCCESS.getCode()).data(detailNotificationsItem);

        } catch (Exception ex) {
            log.error("[NotificationsServiceImpl.update] Update notifications [{}] exception: ", id, ex);
            detailNotificationsResponse.message(ex.getMessage());
        }
        return detailNotificationsResponse;
    }
}
