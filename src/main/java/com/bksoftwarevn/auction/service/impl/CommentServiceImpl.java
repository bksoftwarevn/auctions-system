package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.CommentMapper;
import com.bksoftwarevn.auction.mapper.UserMapper;
import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;
import com.bksoftwarevn.auction.persistence.entity.CommentEntity;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.CommentRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public CommonResponse delete(String currentUserId, String commentId) {
        return null;
    }

    @Override
    public CommentsResponse search(SearchCommentRequest searchCommentRequest) {
        return null;
    }

    @Override
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
    public CreateCommentResponse update(String userId, UpdateCommentRequest updateCommentRequest) {
        CreateCommentResponse response = new CreateCommentResponse().code(AucMessage.CREATE_COMMENT_FAILED.getCode()).message(AucMessage.CREATE_COMMENT_FAILED.getMessage());

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
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_COMMENT_SUCCESS.getCode()).message(AucMessage.CREATE_COMMENT_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CommentServiceImpl.update] Exception when update comment: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }
}
