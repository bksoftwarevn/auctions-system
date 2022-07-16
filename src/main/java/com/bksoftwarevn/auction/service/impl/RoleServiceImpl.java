package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.model.CreateRoleRequest;
import com.bksoftwarevn.auction.model.CreateRoleResponse;
import com.bksoftwarevn.auction.model.RolesResponse;
import com.bksoftwarevn.auction.model.UpdateRoleRequest;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.dto.RoleDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.RoleMapper;
import com.bksoftwarevn.auction.persistence.entity.RoleEntity;
import com.bksoftwarevn.auction.persistence.repository.RoleRepository;
import com.bksoftwarevn.auction.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleDto getByRole(AuthoritiesConstants roleName) {
        log.info("[RoleServiceImpl.getByRole] Get role by role name: {}", roleName);
        RoleDto roleDto;
        try {
            RoleEntity roleEntity = roleRepository.findByRole(roleName.name());
            roleDto = roleMapper.mapping(roleEntity);
        } catch (Exception ex) {
            log.error("[RoleServiceImpl.getByRole] Exception when get role by role name: {}. Detail: ", roleName, ex);
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage(), ex.getCause(), true);
        }
        return roleDto;
    }

    @Override
    public List<RoleDto> getAllRole() {
        log.info("[RoleServiceImpl.getAllRole] Get all role in system");
        List<RoleDto> roleDtos;
        try {
            List<RoleEntity> roleEntities = roleRepository.findAll();
            roleDtos = roleMapper.mapping(roleEntities);
        } catch (Exception ex) {
            log.error("[RoleServiceImpl.getAllRole] Exception when get all role: ", ex);
            throw new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage(), ex.getCause(), true);
        }
        return roleDtos;
    }

    @Override
    public RolesResponse pull() {
        RolesResponse rolesResponse = new RolesResponse().code(AucMessage.PULL_ROLE_FAILED.getCode()).message(AucMessage.PULL_ROLE_FAILED.getMessage());

        try {
            List<RoleEntity> roleEntities = roleRepository.findAll();
            if (!CollectionUtils.isEmpty(roleEntities)) {
                rolesResponse.data(roleMapper.mappingItem(roleEntities)).code(AucMessage.PULL_ROLE_SUCCESS.getCode()).message(AucMessage.PULL_ROLE_SUCCESS.getMessage());
            } else {
                rolesResponse.data(roleMapper.mappingItem(roleEntities)).code(AucMessage.ROLE_NOT_FOUND.getCode()).message(AucMessage.ROLE_NOT_FOUND.getMessage());
            }
        } catch (Exception ex) {
            log.error("[RoleServiceImpl.pull] Exception when get all role: ", ex);
            rolesResponse.message(ex.getMessage());
        }
        return rolesResponse;
    }

    @Override
    public CreateRoleResponse create(CreateRoleRequest createRoleRequest) {
        CreateRoleResponse createRoleResponse = new CreateRoleResponse().code(AucMessage.CREATE_ROLE_FAILED.getCode()).message(AucMessage.CREATE_ROLE_FAILED.getMessage());

        try {

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole(createRoleRequest.getRole());
            roleEntity.setDescriptions(createRoleRequest.getDescriptions());
            roleEntity.setAdditional(roleEntity.getAdditional());

            roleEntity = roleRepository.save(roleEntity);
            if (ObjectUtils.isNotEmpty(roleEntity)) {
                createRoleResponse.data(roleMapper.mappingEntityToItem(roleEntity)).code(AucMessage.CREATE_ROLE_SUCCESS.getCode()).message(AucMessage.CREATE_ROLE_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[RoleServiceImpl.create] Exception when create role: ", ex);
            createRoleResponse.message(ex.getMessage());
        }
        return createRoleResponse;
    }

    @Override
    public CreateRoleResponse update(UpdateRoleRequest updateRoleRequest) {
        CreateRoleResponse updateResponse = new CreateRoleResponse().code(AucMessage.UPDATE_ROLE_FAILED.getCode()).message(AucMessage.UPDATE_ROLE_FAILED.getMessage());
        try {
            RoleEntity roleEntity = roleRepository.findById(updateRoleRequest.getId()).orElseThrow(() -> new AucException(AucMessage.ROLE_NOT_FOUND.getCode(), AucMessage.ROLE_NOT_FOUND.getMessage()));
            if (ObjectUtils.isNotEmpty(roleEntity)) {
                roleEntity.setRole(updateRoleRequest.getRole());
                roleEntity.setDescriptions(updateRoleRequest.getDescriptions());
                roleEntity = roleRepository.save(roleEntity);
                if (ObjectUtils.isNotEmpty(roleEntity)) {
                    updateResponse.code(AucMessage.UPDATE_ROLE_SUCCESS.getCode()).message(AucMessage.UPDATE_ROLE_SUCCESS.getMessage())
                            .data(roleMapper.mappingEntityToItem(roleEntity));
                }
            }
        } catch (Exception ex) {
            log.error("[RoleServiceImpl.update] Update role [{}] exception: ", updateRoleRequest, ex);
            updateResponse.message(ex.getMessage());
        }
        return updateResponse;
    }
}
