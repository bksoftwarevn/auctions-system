package com.bksoftwarevn.auction.service;


import com.bksoftwarevn.auction.model.BrandsResponse;
import com.bksoftwarevn.auction.model.CreateBrandRequest;
import com.bksoftwarevn.auction.model.CreateBrandResponse;
import com.bksoftwarevn.auction.model.UpdateBrandRequest;

public interface BrandService {

    BrandsResponse pull();

    CreateBrandResponse create(CreateBrandRequest createBrandRequest);

    CreateBrandResponse update(UpdateBrandRequest updateBrandRequest);
}
