package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.RoleDto;
import com.bksoftwarevn.auction.model.RoleItem;
import com.bksoftwarevn.auction.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    RoleDto mapping(RoleEntity roleEntity);

    List<RoleDto> mapping(List<RoleEntity> roleEntities);
    List<RoleItem> mappingRoleItems(List<RoleEntity> roleEntities);
    RoleItem mapping(RoleDto roleDto);



}
