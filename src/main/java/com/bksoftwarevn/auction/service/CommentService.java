package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateCommentRequest;
import com.bksoftwarevn.auction.model.CreateCommentResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.model.UpdateCommentRequest;

public interface CommentService {
    CommonResponse delete(String currentUserId, String commentId);

    CommentsResponse search(SearchCommentRequest searchCommentRequest);

    com.bksoftwarevn.auction.model.CreateCommentResponse create(String userId, CreateCommentRequest createCommentRequest);

    CreateCommentResponse update(String userId, UpdateCommentRequest updateCommentRequest);
}
