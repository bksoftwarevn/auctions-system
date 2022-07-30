package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.RoleDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.RoleMapper;
import com.bksoftwarevn.auction.model.CreateRoleRequest;
import com.bksoftwarevn.auction.model.CreateRoleResponse;
import com.bksoftwarevn.auction.model.RolesResponse;
import com.bksoftwarevn.auction.model.UpdateRoleRequest;
import com.bksoftwarevn.auction.persistence.entity.RoleEntity;
import com.bksoftwarevn.auction.persistence.repository.RoleRepository;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoleMapper roleMapper = mock(RoleMapper.class);
    private final RoleServiceImpl roleService = new RoleServiceImpl(roleRepository, roleMapper);
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";
    private static final Integer MOCK_ID_1 = 12;

    @Test
    void getByRole() {
        AuthoritiesConstants roleName = AuthoritiesConstants.ROLE_ADMIN;
        RoleEntity roleEntity = mock(RoleEntity.class);
        RoleDto roleDto = mock(RoleDto.class);

        when(roleRepository.findByRole(roleName.name())).thenReturn(roleEntity);
        when(roleMapper.mapping(roleEntity)).thenReturn(roleDto);

        RoleDto actualResult = roleService.getByRole(roleName);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void getByRole1() {
        AuthoritiesConstants roleName = AuthoritiesConstants.ROLE_ADMIN;

        when(roleRepository.findByRole(roleName.name())).thenThrow(new AucException());

        AucException exception = assertThrows(AucException.class, () -> roleService.getByRole(roleName));
        Assertions.assertNotNull(exception);
    }

    @Test
    void getAllRole() {
        when(roleRepository.findAll()).thenThrow(new AucException());

        AucException exception = assertThrows(AucException.class, () -> roleService.getAllRole());
        Assertions.assertNotNull(exception);
    }

    @Test
    void getAllRole1() {
        List<RoleEntity> roleEntities = mock(List.class);
        List<RoleDto> roleDto = mock(List.class);

        when(roleRepository.findAll()).thenReturn(roleEntities);
        when(roleMapper.mapping(roleEntities)).thenReturn(roleDto);

        List<RoleDto> actualResult = roleService.getAllRole();
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull() {
        when(roleRepository.findAll()).thenReturn(null);

        RolesResponse actualResult = roleService.pull();
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull1() {
        List<RoleEntity> roleEntities = mock(List.class);

        when(roleRepository.findAll()).thenReturn(roleEntities);

        RolesResponse actualResult = roleService.pull();
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull2() {
        when(roleRepository.findAll()).thenThrow(new AucException());

        RolesResponse actualResult = roleService.pull();
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create() {
        CreateRoleRequest createRoleRequest = mock(CreateRoleRequest.class);
        RoleEntity roleEntity = mock(RoleEntity.class);

        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        CreateRoleResponse actualResult = roleService.create(createRoleRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create1() {
        CreateRoleRequest createRoleRequest = mock(CreateRoleRequest.class);

        when(roleRepository.save(any(RoleEntity.class))).thenReturn(null);

        CreateRoleResponse actualResult = roleService.create(createRoleRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create2() {
        CreateRoleRequest createRoleRequest = mock(CreateRoleRequest.class);

        when(roleRepository.save(any(RoleEntity.class))).thenThrow(new AucException());

        CreateRoleResponse actualResult = roleService.create(createRoleRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update() {
        UpdateRoleRequest updateRoleRequest = mock(UpdateRoleRequest.class);
        RoleEntity roleEntity = mock(RoleEntity.class);

        when(updateRoleRequest.getId()).thenReturn(MOCK_ID_1);
        when(roleRepository.findById(MOCK_ID_1)).thenReturn(Optional.empty());

        CreateRoleResponse actualResult = roleService.update(updateRoleRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update1() {
        UpdateRoleRequest updateRoleRequest = mock(UpdateRoleRequest.class);
        RoleEntity roleEntity = mock(RoleEntity.class);

        when(updateRoleRequest.getId()).thenReturn(MOCK_ID_1);
        when(roleRepository.findById(MOCK_ID_1)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        CreateRoleResponse actualResult = roleService.update(updateRoleRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update2() {
        UpdateRoleRequest updateRoleRequest = mock(UpdateRoleRequest.class);
        RoleEntity roleEntity = mock(RoleEntity.class);

        when(updateRoleRequest.getId()).thenReturn(MOCK_ID_1);
        when(roleRepository.findById(MOCK_ID_1)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(null);

        CreateRoleResponse actualResult = roleService.update(updateRoleRequest);
        Assertions.assertNotNull(actualResult);
    }
}