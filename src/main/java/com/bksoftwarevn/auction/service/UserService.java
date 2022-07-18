package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.model.*;
import org.springframework.web.multipart.MultipartFile;

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

    CommonResponse contactUs(MultipartFile file, String phone, String email, String name, String content, String reportType);
}
