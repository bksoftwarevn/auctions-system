package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.api.v1.AccountApi;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final UserService userService;

    @Override
    public ResponseEntity<CommonResponse> contactUs(MultipartFile file, String name, String phone, String email, String content, String reportType) {
        return ResponseEntity.ok(userService.contactUs(file, name, phone, email, content, reportType));
    }

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
