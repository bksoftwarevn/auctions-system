package com.bksoftwarevn.auction.mapper;

import com.bksoftwarevn.auction.dto.UserDto;
import com.bksoftwarevn.auction.model.UserDataItem;
import com.bksoftwarevn.auction.model.UserDetailCustomize;
import com.bksoftwarevn.auction.model.UserRegisterRequest;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    UserDto mapping(UserEntity userEntity);

    List<UserDto> mapping(List<UserEntity> userEntities);

    UserEntity mapping(UserRegisterRequest userRegisterRequest);


    UserDataItem mappingData(UserRegisterRequest userRegisterRequest);

    @Mappings(
            @Mapping(source = "user.createDate", target = "createDate")
    )
    UserDataItem mapping(UserDetailCustomize user);

    @Mappings(
            @Mapping(source = "user.createdDate", target = "createDate")
    )
    UserDataItem mappingEntity(UserEntity user);
}
