package com.bksoftwarevn.auction.api;


import com.bksoftwarevn.auction.model.AuctionsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateAuctionRequest;
import com.bksoftwarevn.auction.model.CreateAuctionResponse;
import com.bksoftwarevn.auction.model.DetailAuctionResponse;
import com.bksoftwarevn.auction.model.FilterAuctionRequest;
import com.bksoftwarevn.auction.model.FilterAuctionResponse;
import com.bksoftwarevn.auction.model.InfoAuctionResponse;
import com.bksoftwarevn.auction.model.UpdateAuctionRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.bksoftwarevn.auction.api.v1.AuctionApi;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuctionController implements AuctionApi {

    private final AuctionService auctionService;


    @Override
    public ResponseEntity<CommonResponse> deleteAuction(String id) {
        log.info("[AuctionController.getAuctions] Start delete auction: [{}]", id);
        return ResponseEntity.ok(auctionService.delete(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<FilterAuctionResponse> filterAuctions(FilterAuctionRequest filterAuctionRequest) {
        log.info("[AuctionController.getAuctions] Start filter list auction");
        return ResponseEntity.ok(auctionService.filterAuctions(filterAuctionRequest));
    }

    @Override
    public ResponseEntity<InfoAuctionResponse> getAuctionInfo(String auctionId) {
        log.info("[AuctionController.getAuctions] Start get info auction: [{}]", auctionId);
        return ResponseEntity.ok(auctionService.info(SecurityUtils.getCurrentUserId(), auctionId));
    }

    @Override
    public ResponseEntity<AuctionsResponse> getAuctions(Long page, Long size) {
        log.info("[AuctionController.getAuctions] Start get list auction");
        return ResponseEntity.ok(auctionService.pull(SecurityUtils.getCurrentUserId(), page, size));
    }


    @Override
    public ResponseEntity<DetailAuctionResponse> getDetailAuction(String id) {
        log.info("[AuctionController.getAuctions] Start get detail auction: {}", id);
        return ResponseEntity.ok(auctionService.detail(id));
    }

    @Override
    public ResponseEntity<CreateAuctionResponse> postCreateAuction(CreateAuctionRequest createAuctionRequest) {
        log.info("[AuctionController.postCreateAuction] Start create auction: {}", createAuctionRequest);
        return ResponseEntity.ok(auctionService.create(createAuctionRequest, SecurityUtils.getCurrentUserId()));
    }

    @Override
    public ResponseEntity<CreateAuctionResponse> putUpdateAuction(UpdateAuctionRequest updateAuctionRequest) {
        log.info("[AuctionController.putUpdateAuction] Start update category: {}", updateAuctionRequest);
        return ResponseEntity.ok(auctionService.update(updateAuctionRequest));
    }
}
