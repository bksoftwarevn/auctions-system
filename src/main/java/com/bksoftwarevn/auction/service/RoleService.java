package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.CreateRoleRequest;
import com.bksoftwarevn.auction.model.CreateRoleResponse;
import com.bksoftwarevn.auction.model.RolesResponse;
import com.bksoftwarevn.auction.model.UpdateRoleRequest;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto getByRole(AuthoritiesConstants roleName);

    List<RoleDto> getAllRole();

    RolesResponse pull();

    CreateRoleResponse create(CreateRoleRequest createRoleRequest);

    CreateRoleResponse update(UpdateRoleRequest updateRoleRequest);
}
