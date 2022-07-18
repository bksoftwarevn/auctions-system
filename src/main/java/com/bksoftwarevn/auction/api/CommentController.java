package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.BrandsResponse;
import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateBrandRequest;
import com.bksoftwarevn.auction.model.CreateBrandResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.UpdateBrandRequest;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.BrandService;
import com.bksoftwarevn.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController implements com.bksoftwarevn.auction.api.v1.CommentApi {

    private final CommentService commentService;

    @Override
    public ResponseEntity<CommonResponse> deleteComment(String id) {
        log.info("[CommentController.deleteComment] Start delete comment: {}", id);
        return ResponseEntity.ok(commentService.delete(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<CommentsResponse> getComments(SearchCommentRequest searchCommentRequest) {
        log.info("[CommentController.getComments] Start get list with data: {}", searchCommentRequest);
        return ResponseEntity.ok(commentService.search(searchCommentRequest));
    }


    @Override
    public ResponseEntity<CreateCommentResponse> postCreateComment(CreateCommentRequest createCommentRequest) {
        log.info("[CommentController.postCreateComment] Start create comment for auctions: {}", createCommentRequest);
        return ResponseEntity.ok(commentService.create(SecurityUtils.getCurrentUserId(),createCommentRequest));
    }

    @Override
    public ResponseEntity<CreateCommentResponse> putUpdateComment(UpdateCommentRequest updateCommentRequest) {
        log.info("[CommentController.putUpdateComment] Start update comment for auctions: {}", updateCommentRequest);
        return ResponseEntity.ok(commentService.update(SecurityUtils.getCurrentUserId(),updateCommentRequest));
    }
}
