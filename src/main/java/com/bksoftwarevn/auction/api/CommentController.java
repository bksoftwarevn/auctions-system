package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.CreateLikeRequest;
import com.bksoftwarevn.auction.model.CreateLikeResponse;
import com.bksoftwarevn.auction.model.SearchAuctionLikedRequest;
import com.bksoftwarevn.auction.model.SearchAuctionsLikedResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
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
    public ResponseEntity<SearchAuctionsLikedResponse> getAuctionsLiked(SearchAuctionLikedRequest searchAuctionLikedRequest) {
        log.info("[CommentController.getAuctionsLiked] Start get Auctions Liked: {}", searchAuctionLikedRequest);
        return ResponseEntity.ok(commentService.getAuctionsLiked(SecurityUtils.getCurrentUserId(), searchAuctionLikedRequest));
    }

    @Override
    public ResponseEntity<CommentsResponse> getComments(SearchCommentRequest searchCommentRequest) {
        log.info("[CommentController.getComments] Start get list with data: {}", searchCommentRequest);
        return ResponseEntity.ok(commentService.search(searchCommentRequest));
    }

    @Override
    public ResponseEntity<com.bksoftwarevn.auction.model.TotalLikeResponse> getTotalLikedAuction(String id) {
        log.info("[CommentController.getTotalLikedAuction] Start get total like auctions id: {}", id);
        return ResponseEntity.ok(commentService.totalLiked(id));
    }

    @Override
    public ResponseEntity<CreateLikeResponse> postCreateLike(CreateLikeRequest createLikeRequest) {
        log.info("[CommentController.postCreateLike] Start create like or unlike: {}", createLikeRequest);
        return ResponseEntity.ok(commentService.likeOrUnlike(SecurityUtils.getCurrentUserId(), createLikeRequest));
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
