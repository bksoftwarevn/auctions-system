package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.CreateProductRequest;
import com.bksoftwarevn.auction.model.CreateProductResponse;
import com.bksoftwarevn.auction.model.UpdateProductRequest;

public interface ProductService {

    CreateProductResponse detail(String id);

    CreateProductResponse create(CreateProductRequest createProductRequest);

    CreateProductResponse update(UpdateProductRequest updateProductRequest);
}
