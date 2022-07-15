package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.ChangePasswordRequest;
import com.bksoftwarevn.auction.model.ChangePasswordResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.UpdateUserRequest;
import com.bksoftwarevn.auction.model.UserRegisterResponse;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.bksoftwarevn.auction.api.v1.ClientApi;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final UserService userService;


    @Override
    public ResponseEntity<UserRegisterResponse> getAccount() {
        return ResponseEntity.ok(userService.getAccount(SecurityUtils.getCurrentUserId()));
    }

    @Override
    public ResponseEntity<ChangePasswordResponse> postChangePassword(ChangePasswordRequest changePasswordRequest) {
        String userId = SecurityUtils.getCurrentUserId();
        log.info("[ClientController.getAccount] User [{}] request change password", userId);
        return ResponseEntity.ok(userService.postChangePassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getPassword()));
    }

    @Override
    public ResponseEntity<CommonResponse> postLogoutAccount() {
        return null;
    }

    @Override
    public ResponseEntity<UserRegisterResponse> postUpdateAccount(UpdateUserRequest updateUserRequest) {
        String userId = SecurityUtils.getCurrentUserId();
        log.info("[ClientController.getAccount] User [{}] request change password", userId);
        return ResponseEntity.ok(userService.postUpdateAccount(userId, updateUserRequest));

    }
}
