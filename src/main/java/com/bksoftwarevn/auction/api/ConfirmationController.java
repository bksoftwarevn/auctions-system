package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ConfirmRequest;
import com.bksoftwarevn.auction.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.bksoftwarevn.auction.api.v1.ConfirmationApi;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConfirmationController implements ConfirmationApi {
    private final ConfirmationService confirmationService;
    @Override
    public ResponseEntity<CommonResponse> postConfirmByOTP(ConfirmRequest confirmRequest) {
        log.info("[ConfirmationController.postConfirmByOTP] start confirm otp with data: {}", confirmRequest);
        return ResponseEntity.ok(confirmationService.confirm(confirmRequest));
    }
}
