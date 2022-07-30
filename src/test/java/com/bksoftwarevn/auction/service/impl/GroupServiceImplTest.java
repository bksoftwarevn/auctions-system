package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.GroupMapper;
import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import com.bksoftwarevn.auction.persistence.repository.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final GroupRepository groupRepository = mock(GroupRepository.class);
    private final GroupMapper mapper = mock(GroupMapper.class);
    private final GroupServiceImpl groupService = new GroupServiceImpl(groupRepository, mapper);

    @Test
    void create() {
        CreateCategoryGroupRequest createCategoryGroupRequest = mock(CreateCategoryGroupRequest.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(groupRepository.save(any(GroupEntity.class))).thenReturn(groupEntity);

        CreateCategoryGroupResponse actualResult = groupService.create(createCategoryGroupRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create1() {
        CreateCategoryGroupRequest createCategoryGroupRequest = mock(CreateCategoryGroupRequest.class);

        when(groupRepository.save(any(GroupEntity.class))).thenReturn(null);

        CreateCategoryGroupResponse actualResult = groupService.create(createCategoryGroupRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create2() {
        CreateCategoryGroupRequest createCategoryGroupRequest = mock(CreateCategoryGroupRequest.class);

        when(groupRepository.save(any(GroupEntity.class))).thenThrow(new AucException());

        CreateCategoryGroupResponse actualResult = groupService.create(createCategoryGroupRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull() {
        List<GroupEntity> groupEntities = mock(List.class);

        when(groupRepository.findAll()).thenReturn(groupEntities);

        GroupsResponse actualResult = groupService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull1() {
        when(groupRepository.findAll()).thenReturn(null);

        GroupsResponse actualResult = groupService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void pull2() {
        when(groupRepository.findAll()).thenThrow(new AucException());

        GroupsResponse actualResult = groupService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update() {
        UpdateCategoryGroupRequest updateCategoryGroupRequest = mock(UpdateCategoryGroupRequest.class);

        when(updateCategoryGroupRequest.getId()).thenReturn(MOCK_ID);
        when(groupRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateCategoryGroupResponse actualResult = groupService.update(updateCategoryGroupRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update1() {
        UpdateCategoryGroupRequest updateCategoryGroupRequest = mock(UpdateCategoryGroupRequest.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(updateCategoryGroupRequest.getId()).thenReturn(MOCK_ID);
        when(updateCategoryGroupRequest.getName()).thenReturn(MOCK_ID);
        when(updateCategoryGroupRequest.getType()).thenReturn(MOCK_ID);
        when(updateCategoryGroupRequest.getDescriptions()).thenReturn(MOCK_ID);
        when(groupRepository.findById(MOCK_ID)).thenReturn(Optional.of(groupEntity));

        CreateCategoryGroupResponse actualResult = groupService.update(updateCategoryGroupRequest);
        Assertions.assertNotNull(actualResult);
    }
}