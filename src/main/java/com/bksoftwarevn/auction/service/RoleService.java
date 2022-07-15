package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto getByRole(AuthoritiesConstants roleName);
    List<RoleDto> getAllRole();
}
