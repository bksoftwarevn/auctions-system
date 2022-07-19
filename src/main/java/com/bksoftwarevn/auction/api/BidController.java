package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.AcceptBidRequest;
import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.BidsResponse;
import com.bksoftwarevn.auction.model.CreateBidRequest;
import com.bksoftwarevn.auction.model.CreateBidResponse;
import com.bksoftwarevn.auction.model.SearchBidRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class BidController implements com.bksoftwarevn.auction.api.v1.BidApi {

    private final BidService bidService;


    @Override
    public ResponseEntity<BidsResponse> getBids(SearchBidRequest searchBidRequest) {
        log.info("[BidController.getBids] Start get list bid with data: {}", searchBidRequest);
        return ResponseEntity.ok(bidService.search(searchBidRequest));
    }

    @Override
    public ResponseEntity<AcceptBidResponse> postAcceptedBid(AcceptBidRequest acceptBidRequest) {
        log.info("[BidController.postAcceptedBid] Start accepted bid with data: {}", acceptBidRequest);
        return ResponseEntity.ok(bidService.accept(acceptBidRequest));
    }

    @Override
    public ResponseEntity<CreateBidResponse> postCreateBid(CreateBidRequest createBidRequest) {
        log.info("[BidController.postCreateBid] Start create bid for auctions: {}", createBidRequest);
        return ResponseEntity.ok(bidService.create(SecurityUtils.getCurrentUserId(), createBidRequest));
    }
}
