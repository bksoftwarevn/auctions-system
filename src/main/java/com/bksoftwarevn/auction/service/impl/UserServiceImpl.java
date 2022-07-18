package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.config.OTPConfiguration;
import com.bksoftwarevn.auction.constant.*;
import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.RoleMapper;
import com.bksoftwarevn.auction.mapper.UserMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.RoleRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.security.jwt.JWTProvider;
import com.bksoftwarevn.auction.service.ConfirmationService;
import com.bksoftwarevn.auction.service.EmailService;
import com.bksoftwarevn.auction.service.OTPService;
import com.bksoftwarevn.auction.service.UserService;
import com.bksoftwarevn.auction.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTProvider tokenProvider;
    private final EmailService emailService;
    private final ConfirmationService confirmationService;
    private final OTPService otpService;
    private final OTPConfiguration otpConfiguration;
    private final RoleMapper roleMapper;


    public UserDto getUserByUsername(String username) {
        UserDto userDto;
        log.info("[UserServiceImpl.getUserByUsername] Get user by username: {}", username);
        try {
            UserEntity userEntity = userRepository.findByUsername(username);
            userDto = userMapper.mapping(userEntity);
        } catch (Exception ex) {
            log.error("[UserServiceImpl.getUserByUsername] Exception when get user by username: {}", username, ex);
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage(), ex.getCause(), true);
        }
        return userDto;
    }

    public UserDto getUserByEmail(String email) {
        UserDto userDto;
        log.info("[UserServiceImpl.getUserByEmail] Get user by email: {}", email);
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            userDto = userMapper.mapping(userEntity);
        } catch (Exception ex) {
            log.error("[UserServiceImpl.getUserByEmail] Exception when get user by email: {}", email, ex);
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage(), ex.getCause(), true);
        }
        return userDto;
    }

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse = buildUserRegisterResponse(userRegisterRequest);
        try {

            if (!ObjectUtils.isEmpty(userRepository.findByUsernameOrEmail(userRegisterRequest.getUsername().trim().toLowerCase(), userRegisterRequest.getEmail().toLowerCase().trim()))) {
                throw new AucException(AucMessage.USERNAME_ALREADY_USED.getCode(), AucMessage.USERNAME_ALREADY_USED.getMessage());
            }

            UserEntity userEntity = userRepository.save(buildUserRegister(userRegisterRequest));

            //send mail key active
            emailService.sendEmail(emailService.buildEmailRequestActiveUser(userEntity));

            userRegisterResponse.code(AucMessage.REGISTER_USER_SUCCESS.getCode());
            userRegisterResponse.data(userRegisterResponse.getData().roles(roleMapper.mappingRoleItems(List.copyOf(userEntity.getRoles()))));
        } catch (Exception ex) {
            log.error("[UserServiceImpl.registerUser] Exception when register new user: ", ex);
            userRegisterResponse.message(ex.getMessage());
        }
        return userRegisterResponse;
    }


    @Override
    public AuthenResponse authenticate(AuthenRequest authenRequest) {
        AuthenResponse authenResponse;
        try {
            Authentication authentication = authenticate(authenRequest.getUsername(), authenRequest.getPassword());
            String token = tokenProvider.createToken(authentication, Boolean.FALSE);
            authenResponse = buildAuthenResponse(token, authentication);
        } catch (AucException ex) {
            log.error("[UserServiceImpl.buildUserRegisterResponse] AucException when build user register response: ", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("[UserServiceImpl.buildUserRegisterResponse] Exception when build user register response: ", ex);
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
        }
        return authenResponse;
    }

    @Override
    public ActiveResponse active(ActiveRequest activeRequest) {
        ActiveResponse authenResponse = new ActiveResponse().code(AucMessage.ACTIVE_USER_FAILED.getCode()).message(AucMessage.ACTIVE_USER_FAILED.getMessage());
        try {
            UserEntity userEntity = userRepository.findByUsername(activeRequest.getUsername());

            if (userEntity != null && (userEntity.getActive() || StringUtils.isNotEmpty(userEntity.getActiveKey()) && userEntity.getActiveKey().equals(activeRequest.getKey()))) {
                userEntity.setActive(Boolean.TRUE);
                userEntity.setActiveKey(null);
                userEntity.setUpdatedDate(Instant.now());
                authenResponse.code(AucMessage.ACTIVE_USER_SUCCESS.getCode()).message(AucMessage.ACTIVE_USER_SUCCESS.getMessage());
            }

        } catch (Exception ex) {
            log.error("[UserServiceImpl.active] Exception when active user: {}", activeRequest.getUsername(), ex);
        }
        return authenResponse;
    }

    @Override
    public CommonResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        CommonResponse response = new CommonResponse().code(AucMessage.RESET_PASSWORD_FAILED.getCode()).message(AucMessage.RESET_PASSWORD_FAILED.getMessage());
        Instant now = Instant.now();
        try {
            if (StringUtils.isNotEmpty(resetPasswordRequest.getUsername())) {
                UserEntity userEntity = userRepository.findByUsername(resetPasswordRequest.getUsername());
                if (!ObjectUtils.isEmpty(userEntity)) {
                    List<ConfirmationDto> confirmationDtos = confirmationService.find(userEntity.getUsername(), ActionType.RESET_PASS.name(), ActionStatus.PENDING.name());
                    if (confirmationDtos.isEmpty() || confirmationDtos.stream().noneMatch(confirmationDto -> confirmationDto.getExpireDate().isAfter(now))) {
                        String otp = String.valueOf(otpService.generateOTP(userEntity.getUsername()));
                        ConfirmationDto confirmationDto = ConfirmationDto.builder()
                                .id(UUID.randomUUID().toString()).username(userEntity.getUsername())
                                .action(ActionType.RESET_PASS.name()).status(ActionStatus.PENDING.name())
                                .data(passwordEncoder.encode(resetPasswordRequest.getPassword().trim()))
                                .otp(otp).expireDate(new Date(System.currentTimeMillis() + otpConfiguration.getDuration() * 60000).toInstant())
                                .build();
                        if (confirmationService.create(confirmationDto)) {
                            emailService.sendEmail(emailService.buildEmailResetPassword(userEntity, otp));
                            response.code(AucMessage.RESET_PASSWORD_SUCCESS.getCode()).message(AucMessage.RESET_PASSWORD_SUCCESS.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public boolean updatePassword(String username, String data) {
        boolean rs = false;
        try {
            UserEntity userEntity = userRepository.findByUsername(username);
            if (!ObjectUtils.isEmpty(userEntity)) {
                userEntity.setPassword(data);
                userEntity.setUpdatedDate(Instant.now());
                if (userRepository.save(userEntity).getPassword().equals(data)) {
                    rs = true;
                }
            }
        } catch (Exception ex) {
            log.error("[UserServiceImpl.updatePassword] Update password for {} exception: ", username, ex);
        }
        return rs;
    }

    @Override
    public UserRegisterResponse getAccount(String currentUserId) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse().code(AucMessage.GET_USER_FAILED.getCode()).message(AucMessage.GET_USER_FAILED.getMessage());
        try {
            UserEntity userEntity = userRepository.findById(currentUserId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
            userRegisterResponse.code(AucMessage.GET_USER_SUCCESS.getCode()).message(AucMessage.GET_USER_SUCCESS.getMessage())
                    .data(userMapper.mappingEntity(userEntity));
        } catch (Exception ex) {
            log.error("[UserServiceImpl.getAccount] Get account {}. Exception: ", currentUserId, ex);
            userRegisterResponse.code(AucMessage.USERNAME_NOT_FOUND.getCode()).message(ex.getMessage());
        }
        return userRegisterResponse;
    }

    @Override
    @Transactional
    public ChangePasswordResponse postChangePassword(String userId, String oldPassword, String password) {
        ChangePasswordResponse userRegisterResponse = new ChangePasswordResponse().code(AucMessage.UPDATE_USER_FAILED.getCode()).message(AucMessage.UPDATE_USER_FAILED.getMessage());
        try {
            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
            if (passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
                String newPass = passwordEncoder.encode(password.trim());
                userEntity.setPassword(newPass);
                userEntity.setUpdatedDate(Instant.now());
                if (userRepository.save(userEntity).getPassword().equals(newPass)) {
                    userRegisterResponse.code(AucMessage.UPDATE_USER_SUCCESS.getCode()).message(AucMessage.UPDATE_USER_SUCCESS.getMessage());
                }
            }
        } catch (Exception ex) {
            log.error("[UserServiceImpl.getAccount] Get account {}. Exception: ", userId, ex);
        }
        return userRegisterResponse;
    }

    @Override
    @Transactional
    public UserRegisterResponse postUpdateAccount(String userId, UpdateUserRequest updateUserRequest) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse().code(AucMessage.UPDATE_USER_FAILED.getCode()).message(AucMessage.UPDATE_USER_FAILED.getMessage());
        try {
            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage()));
            if (StringUtils.isNotEmpty(updateUserRequest.getEmail())) {
                userEntity.setEmail(updateUserRequest.getEmail());
            }
            if (StringUtils.isNotEmpty(updateUserRequest.getAddress())) {
                userEntity.setAddress(updateUserRequest.getAddress());
            }
            if (StringUtils.isNotEmpty(updateUserRequest.getName())) {
                userEntity.setName(updateUserRequest.getName());
            }
            if (StringUtils.isNotEmpty(updateUserRequest.getPhone())) {
                userEntity.setPhone(updateUserRequest.getPhone());
            }
            if (StringUtils.isNotEmpty(updateUserRequest.getCitizenId())) {
                userEntity.setCitizenId(updateUserRequest.getCitizenId());
            }
            if (StringUtils.isNotEmpty(updateUserRequest.getAvatar())) {
                userEntity.setAvatar(updateUserRequest.getAvatar());
            }
            userEntity.setUpdatedDate(Instant.now());

            userRegisterResponse.code(AucMessage.UPDATE_USER_SUCCESS.getCode()).message(AucMessage.UPDATE_USER_SUCCESS.getMessage())
                    .data(userMapper.mappingEntity(userRepository.save(userEntity)));
        } catch (Exception ex) {
            log.error("[UserServiceImpl.getAccount] Update account {}. Exception: ", userId, ex);
        }
        return userRegisterResponse;
    }

    private AuthenResponse buildAuthenResponse(String token, Authentication authentication) {
        AuthenResponse authenResponse = new AuthenResponse().code(AucMessage.UNAUTHORIZED.getCode()).message(AucMessage.UNAUTHORIZED.getMessage());
        try {
            if (StringUtils.isNotEmpty(token)) {
                AuthenDataItem authenDataItem = new AuthenDataItem().accessToken(token)
                        .tokenType(ClaimConstant.TOKEN_TYPE)
                        .expiresIn(String.valueOf(tokenProvider.getExpirationDateFromToken(token).getTime() - System.currentTimeMillis()));
                if (authentication.getPrincipal() instanceof UserDetailCustomize) {
                    authenDataItem.user(userMapper.mapping((UserDetailCustomize) authentication.getPrincipal()));
                }
                authenResponse.code(AucMessage.AUTHEN_SUCCESS.getCode()).message(AucMessage.AUTHEN_SUCCESS.getMessage()).data(authenDataItem);
            }
        } catch (Exception ex) {
            log.error("[UserServiceImpl.buildAuthenResponse] Exception when build authenticate response: ", ex);
            authenResponse.code(AucMessage.UNAUTHORIZED.getCode()).message(ex.getMessage());
        }
        return authenResponse;
    }


    UserRegisterResponse buildUserRegisterResponse(UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse = null;
        try {
            userRegisterResponse = new UserRegisterResponse();
            userRegisterResponse.code(AucMessage.REGISTER_USER_FAILED.getCode());
            userRegisterResponse.message(AucMessage.REGISTER_USER_SUCCESS.getMessage());
            UserDataItem userDataItem = userMapper.mappingData(userRegisterRequest);
            userRegisterResponse.data(userDataItem);
        } catch (Exception ex) {
            log.error("[UserServiceImpl.buildUserRegisterResponse] Exception when build user register response: ", ex);
        }
        return userRegisterResponse;
    }

    UserEntity buildUserRegister(UserRegisterRequest userRegisterRequest) {
        UserEntity userEntity = null;
        try {
            userEntity = userMapper.mapping(userRegisterRequest);
            userEntity.setId(UUID.randomUUID().toString());
            userEntity.setUsername(userEntity.getUsername().trim().toLowerCase());
            userEntity.setEmail(userEntity.getEmail().trim().toLowerCase());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword().trim()));
            userEntity.setActive(Boolean.FALSE);
            userEntity.setCreatedDate(Instant.now());
            userEntity.setLang(Languages.VI.getLanguage());
            userEntity.setLock(Boolean.FALSE);
            userEntity.setRoles(Collections.singleton(roleRepository.findByRole(AuthoritiesConstants.ROLE_USER.name())));
            userEntity.setActiveKey(RandomUtil.generateActivationKey());
        } catch (Exception ex) {
            log.error("[UserServiceImpl.buildUserRegister] Exception when build user register: ", ex);
        }
        return userEntity;
    }

    private Authentication authenticate(String username, String password) {
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new AucException(AucMessage.USERNAME_DISABLED.getCode(), e.getMessage());
        } catch (BadCredentialsException e) {
            throw new AucException(AucMessage.INVALID_CREDENTIALS.getCode(), e.getMessage());
        } catch (Exception ex) {
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), AucMessage.INVALID_CREDENTIALS.getMessage());
        }
        return authentication;
    }

    @Override
    public CommonResponse contactUs(MultipartFile file, String phone, String email, String name, String content, String reportType) {
        try {
            byte[] fileContent = file.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            EmailBody emailBody = emailService.buildEmailContactUs(file.getOriginalFilename(), encodedString,
                    phone, email, name, content, reportType);
            emailService.sendEmail(emailBody);
        } catch (IOException e) {
            log.error("Cannot convert image to base64 string");
            throw new AucException(AucMessage.CONTACT_US_FAILED.getCode(), AucMessage.CONTACT_US_FAILED.getMessage());
        }
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(AucMessage.CONTACT_US_SUCCESS.getCode());
        commonResponse.setMessage(AucMessage.CONTACT_US_SUCCESS.getMessage());
        return commonResponse;
    }
}
