package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;
import com.bksoftwarevn.auction.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    @Override
    public CreateCategoryGroupResponse create(CreateCategoryGroupRequest createCategoryGroupRequest) {
        return null;
    }

    @Override
    public GroupsResponse pull() {
        return null;
    }

    @Override
    public CreateCategoryGroupResponse update(UpdateCategoryGroupRequest updateCategoryGroupRequest) {
        return null;
    }
}
