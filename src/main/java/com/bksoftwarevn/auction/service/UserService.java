package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.model.ActiveRequest;
import com.bksoftwarevn.auction.model.ActiveResponse;
import com.bksoftwarevn.auction.model.AuthenResponse;
import com.bksoftwarevn.auction.model.ChangePasswordResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ResetPasswordRequest;
import com.bksoftwarevn.auction.model.UpdateUserRequest;
import com.bksoftwarevn.auction.model.UserRegisterRequest;
import com.bksoftwarevn.auction.model.UserRegisterResponse;

public interface UserService {
    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);

    AuthenResponse authenticate(com.bksoftwarevn.auction.model.AuthenRequest authenRequest);

    ActiveResponse active(ActiveRequest activeRequest);

    CommonResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean updatePassword(String username, String data);

    UserRegisterResponse getAccount(String currentUserId);

    ChangePasswordResponse postChangePassword(String userId, String oldPassword, String password);

    UserRegisterResponse postUpdateAccount(String userId, UpdateUserRequest updateUserRequest);
}
