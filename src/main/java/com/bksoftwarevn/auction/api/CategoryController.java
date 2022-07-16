package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;
import com.bksoftwarevn.auction.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.bksoftwarevn.auction.api.v1.CategoryApi;


@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final GroupService groupService;


    @Override
    public ResponseEntity<GroupsResponse> getCategoryGroups() {
        log.info("[CategoryController.postCreateCategoryGroup] Start get list category group");
        return ResponseEntity.ok(groupService.pull());
    }

    @Override
    public ResponseEntity<CreateCategoryGroupResponse> postCreateCategoryGroup(CreateCategoryGroupRequest createCategoryGroupRequest) {
        log.info("[CategoryController.postCreateCategoryGroup] Start create category group: {}", createCategoryGroupRequest);
        return ResponseEntity.ok(groupService.create(createCategoryGroupRequest));
    }

    @Override
    public ResponseEntity<CreateCategoryGroupResponse> putUpdateCategoryGroup(UpdateCategoryGroupRequest updateCategoryGroupRequest) {
        log.info("[CategoryController.postCreateCategoryGroup] Start create category group: {}", updateCategoryGroupRequest);
        return ResponseEntity.ok(groupService.update(updateCategoryGroupRequest));
    }
}
