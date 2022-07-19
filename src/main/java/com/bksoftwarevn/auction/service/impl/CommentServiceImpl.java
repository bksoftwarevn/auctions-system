package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.CommentMapper;
import com.bksoftwarevn.auction.mapper.LikeAuctionMapper;
import com.bksoftwarevn.auction.model.AuctionLikedItem;
import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.CreateLikeRequest;
import com.bksoftwarevn.auction.model.CreateLikeResponse;
import com.bksoftwarevn.auction.model.SearchAuctionLikedRequest;
import com.bksoftwarevn.auction.model.SearchAuctionsLikedResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.TotalLikeResponse;
import com.bksoftwarevn.auction.model.TotalLikedItem;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.filter.Condition;
import com.bksoftwarevn.auction.persistence.filter.Operator;
import com.bksoftwarevn.auction.persistence.filter.Order;
import com.bksoftwarevn.auction.persistence.filter.SortType;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.CommentRepository;
import com.bksoftwarevn.auction.persistence.repository.LikeAuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.CommentService;
import com.bksoftwarevn.auction.service.CommonQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final CommonQueryService commonQueryService;
    private final LikeAuctionRepository likeAuctionRepository;
    private final LikeAuctionMapper likeAuctionMapper;


    @Override
    @Transactional
    public CommonResponse delete(String currentUserId, String commentId) {

        CommonResponse response = new CommonResponse().code(AucMessage.DELETE_COMMENT_FAILED.getCode()).message(AucMessage.DELETE_COMMENT_FAILED.getMessage());

        try {
            CommentEntity entity = repository.findById(commentId).orElseThrow(() -> new AucException(AucMessage.COMMENT_NOT_FOUND.getCode(), AucMessage.COMMENT_NOT_FOUND.getMessage()));
            if (!entity.getUser().getId().equals(currentUserId)) {
                throw new AucException(AucMessage.FORBIDDEN.getCode(), AucMessage.FORBIDDEN.getMessage());
            }
            long rs = repository.deleteByIdAndUserId(entity.getId(), currentUserId);
            if (rs > 0) {
                response.code(AucMessage.DELETE_COMMENT_SUCCESS.getCode()).message(AucMessage.DELETE_COMMENT_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.delete] Exception when delete comment: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CommentsResponse search(SearchCommentRequest searchCommentRequest) {
        CommentsResponse response = new CommentsResponse().code(AucMessage.AUCTION_NOT_FOUND.getCode()).message(AucMessage.AUCTION_NOT_FOUND.getMessage());
        try {

            AuctionEntity auctionEntity = auctionRepository.findById(searchCommentRequest.getAuctionId()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));

            List<Order> orders = new ArrayList<>();
            Order order = Order.builder()
                    .field(CommentEntity_.CREATED_DATE)
                    .sortType(SortType.ASC).build();
            orders.add(order);
            List<Condition> conditions = new ArrayList<>();

            conditions.add(Condition.builder()
                    .field(CommentEntity_.AUCTION)
                    .operator(Operator.EQUALS)
                    .value(auctionEntity)
                    .build());


            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchCommentRequest.getPage())
                    .size(searchCommentRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<CommentEntity> auctionItemPaginationDTO = commonQueryService.search(CommentEntity.class, CommentEntity.class, searchDTO, null);

            response.code(AucMessage.PULL_AUCTION_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage())
                    .data(mapper.mappingEntitiesToItems(auctionItemPaginationDTO.getItems())).total(auctionItemPaginationDTO.getTotal());

        } catch (Exception ex) {
            log.error("[CommentServiceImpl.search] search comment have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateCommentResponse create(String userId, CreateCommentRequest createCommentRequest) {
        CreateCommentResponse response = new CreateCommentResponse().code(AucMessage.CREATE_COMMENT_FAILED.getCode()).message(AucMessage.CREATE_COMMENT_FAILED.getMessage());

        try {
            CommentEntity entity = new CommentEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setAuction(auctionRepository.findByIdAndStatus(createCommentRequest.getAuctionId(), AuctionStatus.STARTING.name()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage())));
            entity.setUser(userRepository.findById(userId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())));
            entity.setContent(createCommentRequest.getContent());
            entity.setCreatedDate(Instant.now());
            entity.setStatus(ActionStatus.CREATED.name());
            if (StringUtils.isNotEmpty(createCommentRequest.getParentId())) {
                entity.setParentId(repository.findById(createCommentRequest.getParentId()).orElseThrow(() -> new AucException(AucMessage.COMMENT_NOT_FOUND.getCode(), AucMessage.COMMENT_NOT_FOUND.getMessage())).getId());
            }
            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_COMMENT_SUCCESS.getCode()).message(AucMessage.CREATE_COMMENT_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.create] Exception when create comment: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateCommentResponse update(String userId, UpdateCommentRequest updateCommentRequest) {
        CreateCommentResponse response = new CreateCommentResponse().code(AucMessage.UPDATE_COMMENT_FAILED.getCode()).message(AucMessage.UPDATE_COMMENT_FAILED.getMessage());

        try {
            CommentEntity entity = repository.findById(updateCommentRequest.getId()).orElseThrow(() -> new AucException(AucMessage.COMMENT_NOT_FOUND.getCode(), AucMessage.COMMENT_NOT_FOUND.getMessage()));
            if (!entity.getUser().getId().equals(userId)) {
                throw new AucException(AucMessage.FORBIDDEN.getCode(), AucMessage.FORBIDDEN.getMessage());
            }
            entity.setContent(updateCommentRequest.getContent());
            entity.setStatus(ActionStatus.UPDATED.name());
            entity.setUpdatedDate(Instant.now());
            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.UPDATE_COMMENT_SUCCESS.getCode()).message(AucMessage.UPDATE_COMMENT_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.update] Exception when update comment: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public SearchAuctionsLikedResponse getAuctionsLiked(String currentUserId, SearchAuctionLikedRequest searchAuctionLikedRequest) {
        SearchAuctionsLikedResponse response = new SearchAuctionsLikedResponse().code(AucMessage.PULL_LIKE_FAILED.getCode()).message(AucMessage.PULL_LIKE_FAILED.getMessage());
        try {


            List<Order> orders = new ArrayList<>();

            List<Condition> conditions = new ArrayList<>();

            conditions.add(Condition.builder()
                    .field(LikeAuctionEntity_.USER)
                    .operator(Operator.EQUALS)
                    .value(userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())))
                    .build());


            SearchDTO searchDTO = SearchDTO.builder()
                    .page(searchAuctionLikedRequest.getPage())
                    .size(searchAuctionLikedRequest.getSize())
                    .orders(orders)
                    .conditions(conditions)
                    .build();

            PaginationDTO<LikeAuctionEntity> auctionItemPaginationDTO = commonQueryService.search(LikeAuctionEntity.class, LikeAuctionEntity.class, searchDTO, null);

            List<AuctionLikedItem> auctionLikedItems = new ArrayList<>();

            auctionItemPaginationDTO.getItems().stream().parallel().forEach(likeAuctionEntity -> {
                AuctionLikedItem auctionLikedItem = likeAuctionMapper.mappingAuctionToItem(likeAuctionEntity);
                auctionLikedItem.setIsLiked(likeAuctionEntity.getIsLiked());
                auctionLikedItems.add(auctionLikedItem);
            });

            response.code(AucMessage.PULL_LIKE_SUCCESS.getCode()).message(AucMessage.PULL_LIKE_SUCCESS.getMessage())
                    .data(auctionLikedItems).total(auctionItemPaginationDTO.getTotal());

        } catch (Exception ex) {
            log.error("[CommentServiceImpl.search] search comment have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public TotalLikeResponse totalLiked(String id) {
        TotalLikeResponse response = new TotalLikeResponse().code(AucMessage.PULL_LIKE_FAILED.getCode()).message(AucMessage.PULL_LIKE_FAILED.getMessage());
        try {
            List<Condition> conditions = new ArrayList<>();

            conditions.add(Condition.builder()
                    .field(LikeAuctionEntity_.AUCTION)
                    .operator(Operator.EQUALS)
                    .value(auctionRepository.findById(id).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage())))
                    .build());
            conditions.add(Condition.builder()
                    .field(LikeAuctionEntity_.IS_LIKED)
                    .operator(Operator.EQUALS)
                    .value(true)
                    .build());

            response.code(AucMessage.PULL_LIKE_SUCCESS.getCode()).message(AucMessage.PULL_LIKE_SUCCESS.getMessage())
                    .data(new TotalLikedItem().totalLiked(commonQueryService.count(LikeAuctionEntity.class,conditions)).auctionId(id));
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.totalLiked] count total like have exception: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CreateLikeResponse likeOrUnlike(String currentUserId, CreateLikeRequest createLikeRequest) {
        CreateLikeResponse response = new CreateLikeResponse().code(AucMessage.LIKE_SUCCESS.getCode()).message(AucMessage.LIKE_SUCCESS.getMessage());

        try {
            LikeAuctionEntityId likeAuctionEntityId = new LikeAuctionEntityId();
            likeAuctionEntityId.setAuctionId(createLikeRequest.getAuctionId());
            likeAuctionEntityId.setUserId(currentUserId);
            LikeAuctionEntity entity = likeAuctionRepository.findById(likeAuctionEntityId).orElse(new LikeAuctionEntity());
            AuctionEntity auctionEntity = auctionRepository.findById(likeAuctionEntityId.getAuctionId()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));
            if (AuctionStatus.REJECTED.name().equals(auctionEntity.getStatus()) || AuctionStatus.PENDING.name().equals(auctionEntity.getStatus())) {
                throw new AucException(AucMessage.CANNOT_LIKE.getCode(), AucMessage.CANNOT_LIKE.getMessage());
            }
            entity.setAuction(auctionEntity);
            entity.setUser(userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage())));
            entity.setId(likeAuctionEntityId);
            entity.setIsLiked(entity.getIsLiked() == null || !entity.getIsLiked());
            entity = likeAuctionRepository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(likeAuctionMapper.mappingEntityToItem(entity)).code(AucMessage.LIKE_SUCCESS.getCode()).message(AucMessage.LIKE_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.likeOrUnlike] Exception when like or unlike : ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }
}
