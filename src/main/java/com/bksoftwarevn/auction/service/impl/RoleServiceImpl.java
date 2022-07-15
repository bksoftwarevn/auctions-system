package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.dto.RoleDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.RoleMapper;
import com.bksoftwarevn.auction.persistence.entity.RoleEntity;
import com.bksoftwarevn.auction.persistence.repository.RoleRepository;
import com.bksoftwarevn.auction.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
