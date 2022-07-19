package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.ChangePasswordRequest;
import com.bksoftwarevn.auction.model.ChangePasswordResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateNewsResponse;
import com.bksoftwarevn.auction.model.DetailNotificationsResponse;
import com.bksoftwarevn.auction.model.SearchNotificationsResponse;
import com.bksoftwarevn.auction.model.UpdateUserRequest;
import com.bksoftwarevn.auction.model.UserRegisterResponse;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.NewsService;
import com.bksoftwarevn.auction.service.NotificationsService;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.bksoftwarevn.auction.api.v1.AccountApi;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final UserService userService;
    private final NewsService newsService;
    private final NotificationsService notificationsService;


    @Override
    public ResponseEntity<UserRegisterResponse> getAccount() {
        return ResponseEntity.ok(userService.getAccount(SecurityUtils.getCurrentUserId()));
    }

    @Override
    public ResponseEntity<CreateNewsResponse> getNewDetail(String id) {
        log.info("[ClientController.getNewDetail] User read news detail [{}] request", id);
        return ResponseEntity.ok(newsService.detail(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<DetailNotificationsResponse> getNotificationsDetail(String id) {
        log.info("[ClientController.getNotificationsDetail] User read notifications detail [{}] request", id);
        return ResponseEntity.ok(notificationsService.detail(SecurityUtils.getCurrentUserId(), id));
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
    public ResponseEntity<CommonResponse> postReadNews(String id) {
        log.info("[ClientController.postReadNews] User read news [{}] request", id);
        return ResponseEntity.ok(newsService.read(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<UserRegisterResponse> postUpdateAccount(UpdateUserRequest updateUserRequest) {
        String userId = SecurityUtils.getCurrentUserId();
        log.info("[ClientController.getAccount] User [{}] request change password", userId);
        return ResponseEntity.ok(userService.postUpdateAccount(userId, updateUserRequest));

    }
}
