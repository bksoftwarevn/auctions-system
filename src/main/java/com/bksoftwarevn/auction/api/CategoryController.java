package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.CategoriesResponse;
import com.bksoftwarevn.auction.model.CreateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.CreateCategoryGroupResponse;
import com.bksoftwarevn.auction.model.CreateCategoryRequest;
import com.bksoftwarevn.auction.model.CreateCategoryResponse;
import com.bksoftwarevn.auction.model.GroupsResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryGroupRequest;
import com.bksoftwarevn.auction.model.UpdateCategoryRequest;
import com.bksoftwarevn.auction.service.CategoryService;
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
    private final CategoryService categoryService;


    @Override
    public ResponseEntity<CategoriesResponse> getCategories() {
        log.info("[CategoryController.getCategories] Start get list category");
        return ResponseEntity.ok(categoryService.pull());
    }

    @Override
    public ResponseEntity<GroupsResponse> getCategoryGroups() {
        log.info("[CategoryController.postCreateCategoryGroup] Start get list category group");
        return ResponseEntity.ok(groupService.pull());
    }

    @Override
    public ResponseEntity<CreateCategoryResponse> postCreateCategory(CreateCategoryRequest createCategoryRequest) {
        log.info("[CategoryController.postCreateCategory] Start create category: {}", createCategoryRequest);
        return ResponseEntity.ok(categoryService.create(createCategoryRequest));
    }

    @Override
    public ResponseEntity<CreateCategoryGroupResponse> postCreateCategoryGroup(CreateCategoryGroupRequest createCategoryGroupRequest) {
        log.info("[CategoryController.postCreateCategoryGroup] Start create category group: {}", createCategoryGroupRequest);
        return ResponseEntity.ok(groupService.create(createCategoryGroupRequest));
    }

    @Override
    public ResponseEntity<CreateCategoryResponse> putUpdateCategory(UpdateCategoryRequest updateCategoryRequest) {
        log.info("[CategoryController.putUpdateCategory] Start update category: {}", updateCategoryRequest);
        return ResponseEntity.ok(categoryService.update(updateCategoryRequest));
    }

    @Override
    public ResponseEntity<CreateCategoryGroupResponse> putUpdateCategoryGroup(UpdateCategoryGroupRequest updateCategoryGroupRequest) {
        log.info("[CategoryController.postCreateCategoryGroup] Start update category group: {}", updateCategoryGroupRequest);
        return ResponseEntity.ok(groupService.update(updateCategoryGroupRequest));
    }
}
