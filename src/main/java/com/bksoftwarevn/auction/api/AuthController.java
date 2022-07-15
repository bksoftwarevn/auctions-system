package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.api.v1.AuthApi;
import com.bksoftwarevn.auction.model.ActiveRequest;
import com.bksoftwarevn.auction.model.AuthenRequest;
import com.bksoftwarevn.auction.model.AuthenResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ResetPasswordRequest;
import com.bksoftwarevn.auction.model.UserRegisterRequest;
import com.bksoftwarevn.auction.model.UserRegisterResponse;
import com.bksoftwarevn.auction.security.jwt.JWTFilter;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;

    @Override
    public ResponseEntity<AuthenResponse> postUserAuthenticate(AuthenRequest authenRequest) {
        log.info("[AuthController.postAuthenticate] Authenticate by username: {}", authenRequest.getUsername());
        AuthenResponse authenResponse = userService.authenticate(authenRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + authenResponse.getData().getAccessToken());
        return new ResponseEntity<>(authenResponse, httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> postResetPassword(ResetPasswordRequest resetPasswordRequest) {
        log.info("[AuthController.postResetPassword] Reset password for username: {}", resetPasswordRequest.getUsername());
        return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest));
    }

    @Override
    public ResponseEntity<com.bksoftwarevn.auction.model.ActiveResponse> postUserActive(ActiveRequest activeRequest) {
        log.info("[AuthController.postUserRegister] Register user data: {}", activeRequest.getUsername());
        return ResponseEntity.ok(userService.active(activeRequest));
    }


    @Override
    public ResponseEntity<UserRegisterResponse> postUserRegister(UserRegisterRequest userRegisterRequest) {
        log.info("[AuthController.postUserRegister] Register user data: {}", userRegisterRequest.getUsername());
        return ResponseEntity.ok(userService.registerUser(userRegisterRequest));
    }
}
