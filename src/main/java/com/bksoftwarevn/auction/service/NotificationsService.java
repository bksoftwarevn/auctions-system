package com.bksoftwarevn.auction.service;


import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateNotificationsRequest;
import com.bksoftwarevn.auction.model.SearchNotificationsRequest;
import com.bksoftwarevn.auction.model.SearchNotificationsResponse;
import com.bksoftwarevn.auction.model.UpdateNotificationsRequest;

public interface NotificationsService {

    SearchNotificationsResponse search(SearchNotificationsRequest searchNotificationsRequest);

    CommonResponse create(CreateNotificationsRequest createNotificationsRequest);

    CommonResponse delete(String currentUserId, String id);

    CommonResponse update(UpdateNotificationsRequest updateNotificationsRequest);

    com.bksoftwarevn.auction.model.DetailNotificationsResponse detail(String currentUserId, String id);
}
