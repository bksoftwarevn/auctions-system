package com.bksoftwarevn.auction.security;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.dto.RoleDto;
import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.model.UserDetailCustomize;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.info("[DomainUserDetailsService.loadUserByUsername] Authenticating with username {}", username);

        UserDto userDto;
        if (EmailValidator.getInstance().isValid(username)) {
            userDto = userService.getUserByEmail(username);
        } else {
            userDto = userService.getUserByUsername(username);
        }

        if (ObjectUtils.isEmpty(userDto)) {
            throw new AucException(AucMessage.USERNAME_NOT_FOUND.getCode(), AucMessage.USERNAME_NOT_FOUND.getMessage() + " User: " + username);
        }

        if (Boolean.FALSE.equals(userDto.getActive())) {
            throw new AucException(AucMessage.USERNAME_DISABLED.getCode(), AucMessage.USERNAME_DISABLED.getMessage() + " User: " + username);
        }

        return createSpringSecurityUser(userDto);
    }

    private UserDetailCustomize createSpringSecurityUser(UserDto user) {

        Set<RoleDto> authorityList = user.getRoles();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(authorityList)) {
            grantedAuthorities = authorityList.stream().map(authority -> new SimpleGrantedAuthority(authority.getRole().trim()))
                    .collect(Collectors.toList());
        }
        return new UserDetailCustomize(user.getUsername(), user.getPassword(), grantedAuthorities, user.getId(), user.getEmail(),
                user.getName(), user.getAddress(), user.getPhone(), user.getActive(), user.getLock(), user.getCitizenId(),
                user.getRoles(), user.getAvatar(), user.getLang(), user.getCreatedDate());
    }
}
