package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.model.UserDataItem;
import com.bksoftwarevn.auction.model.UserDetailCustomize;
import com.bksoftwarevn.auction.model.UserRegisterRequest;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {
    UserDto mapping(UserEntity userEntity);

    List<UserDto> mapping(List<UserEntity> userEntities);

    UserEntity mapping(UserRegisterRequest userRegisterRequest);

    UserDataItem mappingData(UserRegisterRequest userRegisterRequest);

    UserDataItem mapping(UserDetailCustomize user);

    UserDataItem mappingEntity(UserEntity user);
}
