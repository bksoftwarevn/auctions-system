package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.AuctionsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateAuctionRequest;
import com.bksoftwarevn.auction.model.CreateAuctionResponse;
import com.bksoftwarevn.auction.model.DetailAuctionResponse;
import com.bksoftwarevn.auction.model.FilterAuctionRequest;
import com.bksoftwarevn.auction.model.FilterAuctionResponse;
import com.bksoftwarevn.auction.model.SearchAuctionRequest;
import com.bksoftwarevn.auction.model.SearchAuctionResponse;
import com.bksoftwarevn.auction.model.UpdateAuctionRequest;
import com.bksoftwarevn.auction.model.UpdateAuctionStatusRequest;

public interface AuctionService {
    com.bksoftwarevn.auction.model.CreateAuctionResponse create(CreateAuctionRequest createAuctionRequest, String userId);

    AuctionsResponse pull(String userId, long page, long size);

    CreateAuctionResponse update(UpdateAuctionRequest updateAuctionRequest);

    FilterAuctionResponse filterAuctions(FilterAuctionRequest filterAuctionRequest);

    DetailAuctionResponse detail(String id);

    com.bksoftwarevn.auction.model.CommonResponse delete(String currentUserId, String id);

    com.bksoftwarevn.auction.model.InfoAuctionResponse info(String currentUserId, String auctionId);

    SearchAuctionResponse search(SearchAuctionRequest searchAuctionRequest);

    CommonResponse updateStatus(UpdateAuctionStatusRequest updateAuctionStatusRequest);
}
