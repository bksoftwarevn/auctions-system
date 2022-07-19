package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.AcceptBidRequest;
import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.BidsResponse;
import com.bksoftwarevn.auction.model.CreateBidRequest;
import com.bksoftwarevn.auction.model.CreateBidResponse;
import com.bksoftwarevn.auction.model.SearchBidRequest;

public interface BidService {
    BidsResponse search(SearchBidRequest searchBidRequest);

    CreateBidResponse create(String currentUserId, CreateBidRequest createBidRequest);

    AcceptBidResponse accept(AcceptBidRequest acceptBidRequest);
}
