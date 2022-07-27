package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.CategoryEntity;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import com.bksoftwarevn.auction.persistence.repository.CategoryRepository;
import com.bksoftwarevn.auction.persistence.repository.GroupRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.slf4j.Logger;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";
    private final String TYPE = "TYPE";
    private final CategoryRepository repository = mock(CategoryRepository.class);
    private final GroupRepository groupRepository = mock(GroupRepository.class);
    private final CategoryMapper mapper = mock(CategoryMapper.class);

    private final CategoryServiceImpl categoryService =
            new CategoryServiceImpl(repository, groupRepository, mapper);

    @Test
    void give_whenPull_then() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        CategoriesResponse actualResult = categoryService.pull();
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenPull_then() {
        CategoryEntity categoryEntity = new CategoryEntity();

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity);

        when(repository.findAll()).thenReturn(categoryEntityList);

        CategoriesResponse actualResult = categoryService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenPull_then() {
        CategoryEntity categoryEntity = new CategoryEntity();

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity);

        when(repository.findAll()).thenReturn(categoryEntityList);
        when(mapper.mappingItems(categoryEntityList)).thenThrow(new AucException(AucMessage.CATEGORY_NOT_FOUND.getCode(), AucMessage.CATEGORY_NOT_FOUND.getMessage()));

        CategoriesResponse actualResult = categoryService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenCreate_then() {
        CreateCategoryRequest createCategoryRequest = mock(CreateCategoryRequest.class);
        GroupEntity groupEntity = mock(GroupEntity.class);
        CategoryEntity entity = mock(CategoryEntity.class);

        when(createCategoryRequest.getType()).thenReturn(TYPE);
        when(groupRepository.findByType(TYPE)).thenReturn(groupEntity);
        when(repository.save(any(CategoryEntity.class))).thenReturn(entity);

        CreateCategoryResponse actualResult = categoryService.create(createCategoryRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenCreate_then() {
        CreateCategoryRequest createCategoryRequest = mock(CreateCategoryRequest.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(createCategoryRequest.getType()).thenReturn(TYPE);
        when(groupRepository.findByType(TYPE)).thenReturn(groupEntity);
        when(repository.save(any(CategoryEntity.class))).thenReturn(null);

        CreateCategoryResponse actualResult = categoryService.create(createCategoryRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenCreate_then() {
        CreateCategoryRequest createCategoryRequest = mock(CreateCategoryRequest.class);

        when(createCategoryRequest.getType()).thenReturn(TYPE);
        when(groupRepository.findByType(TYPE)).thenThrow(new AucException());
        when(repository.save(any(CategoryEntity.class))).thenReturn(null);

        CreateCategoryResponse actualResult = categoryService.create(createCategoryRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenUpdate_then() {
        UpdateCategoryRequest updateCategoryRequest = mock(UpdateCategoryRequest.class);

        when(updateCategoryRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateCategoryResponse actualResult = categoryService.update(updateCategoryRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenUpdate_then() {
        UpdateCategoryRequest updateCategoryRequest = mock(UpdateCategoryRequest.class);
        CategoryEntity entity = mock(CategoryEntity.class);

        when(updateCategoryRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));

        CreateCategoryResponse actualResult = categoryService.update(updateCategoryRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenUpdate_then() {
        UpdateCategoryRequest updateCategoryRequest = mock(UpdateCategoryRequest.class);
        CategoryEntity entity = mock(CategoryEntity.class);

        when(updateCategoryRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        MockedStatic<ObjectUtils> mockedStatic = mockStatic(ObjectUtils.class);
        mockedStatic.when(() -> ObjectUtils.isNotEmpty(any(CategoryEntity.class))).thenReturn(false);

        CreateCategoryResponse actualResult = categoryService.update(updateCategoryRequest);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }
}