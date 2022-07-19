package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.CreateLikeRequest;
import com.bksoftwarevn.auction.model.SearchAuctionLikedRequest;
import com.bksoftwarevn.auction.model.SearchAuctionsLikedResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.TotalLikeResponse;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;

public interface CommentService {
    CommonResponse delete(String currentUserId, String commentId);

    CommentsResponse search(SearchCommentRequest searchCommentRequest);

    com.bksoftwarevn.auction.model.CreateCommentResponse create(String userId, CreateCommentRequest createCommentRequest);

    CreateCommentResponse update(String userId, UpdateCommentRequest updateCommentRequest);

    SearchAuctionsLikedResponse getAuctionsLiked(String currentUserId, SearchAuctionLikedRequest searchAuctionLikedRequest);

    TotalLikeResponse totalLiked(String id);

    com.bksoftwarevn.auction.model.CreateLikeResponse likeOrUnlike(String currentUserId, CreateLikeRequest createLikeRequest);
}
