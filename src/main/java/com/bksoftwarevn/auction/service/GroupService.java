package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;

public interface GroupService {
    CreateCategoryGroupResponse create(CreateCategoryGroupRequest createCategoryGroupRequest);

    GroupsResponse pull();

    CreateCategoryGroupResponse update(UpdateCategoryGroupRequest updateCategoryGroupRequest);
}
