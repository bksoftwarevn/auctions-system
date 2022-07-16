package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.GroupMapper;
import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import com.bksoftwarevn.auction.persistence.repository.GroupRepository;
import com.bksoftwarevn.auction.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper mapper;

    @Override
    public CreateCategoryGroupResponse create(CreateCategoryGroupRequest createCategoryGroupRequest) {
        CreateCategoryGroupResponse response = new CreateCategoryGroupResponse().code(AucMessage.CREATE_GROUP_FAILED.getCode()).message(AucMessage.CREATE_GROUP_FAILED.getMessage());

        try {

            GroupEntity entity = new GroupEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setName(createCategoryGroupRequest.getName());
            entity.setType(createCategoryGroupRequest.getType());
            entity.setDescriptions(createCategoryGroupRequest.getDescriptions());

            entity = groupRepository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_GROUP_SUCCESS.getCode()).message(AucMessage.CREATE_GROUP_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[GroupServiceImpl.create] Exception when create group: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public GroupsResponse pull() {
        GroupsResponse response = new GroupsResponse().code(AucMessage.PULL_GROUP_FAILED.getCode()).message(AucMessage.PULL_GROUP_FAILED.getMessage());

        try {
            List<GroupEntity> groupEntities = groupRepository.findAll();
            if (!CollectionUtils.isEmpty(groupEntities)) {
                response.data(mapper.mappingItems(groupEntities)).code(AucMessage.PULL_GROUP_SUCCESS.getCode()).message(AucMessage.PULL_GROUP_SUCCESS.getMessage());
            } else {
                response.code(AucMessage.GROUP_NOT_FOUND.getCode()).message(AucMessage.GROUP_NOT_FOUND.getMessage());
            }
        } catch (Exception ex) {
            log.error("[GroupServiceImpl.pull] Exception when get all group: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CreateCategoryGroupResponse update(UpdateCategoryGroupRequest updateCategoryGroupRequest) {
        CreateCategoryGroupResponse response = new CreateCategoryGroupResponse().code(AucMessage.UPDATE_GROUP_FAILED.getCode()).message(AucMessage.UPDATE_GROUP_FAILED.getMessage());
        try {
            GroupEntity entity = groupRepository.findById(updateCategoryGroupRequest.getId()).orElseThrow(() -> new AucException(AucMessage.GROUP_NOT_FOUND.getCode(), AucMessage.GROUP_NOT_FOUND.getMessage()));
            if (ObjectUtils.isNotEmpty(entity)) {
                entity.setName(updateCategoryGroupRequest.getName());
                entity.setType(updateCategoryGroupRequest.getType());
                entity.setDescriptions(updateCategoryGroupRequest.getDescriptions());
                if (ObjectUtils.isNotEmpty(entity)) {
                    response.code(AucMessage.UPDATE_GROUP_SUCCESS.getCode()).message(AucMessage.UPDATE_GROUP_SUCCESS.getMessage())
                            .data(mapper.mappingEntityToItem(entity));
                }
            }
        } catch (Exception ex) {
            log.error("[GroupServiceImpl.update] Update group [{}] exception: ", updateCategoryGroupRequest, ex);
            response.message(ex.getMessage());
        }
        return response;
    }
}
