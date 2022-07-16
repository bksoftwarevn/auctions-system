package com.bksoftwarevn.auction.service;


import com.bksoftwarevn.auction.model.CategoriesResponse;
import com.bksoftwarevn.auction.model.CreateCategoryRequest;
import com.bksoftwarevn.auction.model.CreateCategoryResponse;
import com.bksoftwarevn.auction.model.UpdateCategoryRequest;

public interface CategoryService {
    CategoriesResponse pull();

    CreateCategoryResponse create(CreateCategoryRequest createCategoryRequest);

    CreateCategoryResponse update(UpdateCategoryRequest updateCategoryRequest);
}
