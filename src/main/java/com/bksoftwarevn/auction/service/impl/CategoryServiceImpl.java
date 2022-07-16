package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.model.CategoriesResponse;
import com.bksoftwarevn.auction.model.CreateCategoryRequest;
import com.bksoftwarevn.auction.model.CreateCategoryResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryRequest;
import com.bksoftwarevn.auction.persistence.entity.CategoryEntity;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import com.bksoftwarevn.auction.persistence.repository.CategoryRepository;
import com.bksoftwarevn.auction.persistence.repository.GroupRepository;
import com.bksoftwarevn.auction.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final GroupRepository groupRepository;
    private final CategoryMapper mapper;


    @Override
    public CategoriesResponse pull() {
        CategoriesResponse response = new CategoriesResponse().code(AucMessage.PULL_CATEGORY_FAILED.getCode()).message(AucMessage.PULL_CATEGORY_FAILED.getMessage());

        try {
            List<CategoryEntity> entities = repository.findAll();
            if (!CollectionUtils.isEmpty(entities)) {
                response.data(mapper.mappingItems(entities)).code(AucMessage.PULL_CATEGORY_SUCCESS.getCode()).message(AucMessage.PULL_CATEGORY_SUCCESS.getMessage());
            } else {
                response.code(AucMessage.CATEGORY_NOT_FOUND.getCode()).message(AucMessage.CATEGORY_NOT_FOUND.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CategoryServiceImpl.pull] Exception when get all category: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateCategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        CreateCategoryResponse response = new CreateCategoryResponse().code(AucMessage.CREATE_CATEGORY_FAILED.getCode()).message(AucMessage.CREATE_CATEGORY_FAILED.getMessage());

        try {

            GroupEntity groupEntity = groupRepository.findByType(createCategoryRequest.getType());

            CategoryEntity entity = new CategoryEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setName(createCategoryRequest.getName());
            entity.setImage(createCategoryRequest.getImage());
            entity.setGroup(groupEntity);
            entity.setDescriptions(createCategoryRequest.getDescriptions());
            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_CATEGORY_SUCCESS.getCode()).message(AucMessage.CREATE_CATEGORY_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[CategoryServiceImpl.create] Exception when create category: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public CreateCategoryResponse update(UpdateCategoryRequest updateCategoryRequest) {
        CreateCategoryResponse response = new CreateCategoryResponse().code(AucMessage.UPDATE_CATEGORY_FAILED.getCode()).message(AucMessage.UPDATE_CATEGORY_FAILED.getMessage());
        try {
            CategoryEntity entity = repository.findById(updateCategoryRequest.getId()).orElseThrow(() -> new AucException(AucMessage.CATEGORY_NOT_FOUND.getCode(), AucMessage.CATEGORY_NOT_FOUND.getMessage()));
            if (ObjectUtils.isNotEmpty(entity)) {
                entity.setName(updateCategoryRequest.getName());
                entity.setImage(updateCategoryRequest.getImage());
                entity.setDescriptions(updateCategoryRequest.getDescriptions());
                entity.setGroup(groupRepository.findByType(updateCategoryRequest.getType()));

                if (ObjectUtils.isNotEmpty(entity)) {
                    response.code(AucMessage.UPDATE_CATEGORY_SUCCESS.getCode()).message(AucMessage.UPDATE_CATEGORY_SUCCESS.getMessage())
                            .data(mapper.mappingEntityToItem(entity));
                }
            }
        } catch (Exception ex) {
            log.error("[CategoryServiceImpl.update] Update category [{}] exception: ", updateCategoryRequest, ex);
            response.message(ex.getMessage());
        }
        return response;
    }
 
}
