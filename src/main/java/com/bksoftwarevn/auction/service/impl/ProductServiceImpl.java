package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.ProductMapper;
import com.bksoftwarevn.auction.model.CreateProductRequest;
import com.bksoftwarevn.auction.model.CreateProductResponse;
import com.bksoftwarevn.auction.model.UpdateProductRequest;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import com.bksoftwarevn.auction.persistence.entity.ProductEntity;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.BrandRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final AuctionRepository auctionRepository;
    private final BrandRepository brandRepository;


    @Override
    public CreateProductResponse detail(String id) {
        CreateProductResponse response = new CreateProductResponse().code(AucMessage.PRODUCT_NOT_FOUND.getCode()).message(AucMessage.PRODUCT_NOT_FOUND.getMessage());

        try {
            ProductEntity productEntity = repository.findById(id).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));
            response.message(AucMessage.PULL_PRODUCT_SUCCESS.getMessage()).code(AucMessage.PULL_PRODUCT_SUCCESS.getCode())
                    .data(mapper.productEntityToProductItem(productEntity));
        } catch (Exception ex) {
            log.error("[ProductServiceImpl.detail] Exception when get detail product: ", ex);
            response.message(ex.getMessage());
        }

        return response;
    }

    @Override
    public CreateProductResponse create(CreateProductRequest createProductRequest) {
        CreateProductResponse response = new CreateProductResponse().code(AucMessage.CREATE_PRODUCT_FAILED.getCode()).message(AucMessage.CREATE_PRODUCT_FAILED.getMessage());

        try {
            validate(createProductRequest);
            AuctionEntity auctionEntity = auctionRepository.findById(createProductRequest.getAuctionId()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));
            if (!auctionEntity.getUser().getId().equalsIgnoreCase(SecurityUtils.getCurrentUserId())) {
                throw new AucException(AucMessage.FORBIDDEN.getCode(), AucMessage.FORBIDDEN.getMessage());
            }
            if (auctionEntity.getStatus().equalsIgnoreCase(AuctionStatus.PENDING.name())) {
                throw new AucException(AucMessage.CANNOT_CREATE_PRODUCT.getCode(), AucMessage.CANNOT_CREATE_PRODUCT.getMessage());
            }
            BrandEntity brandEntity = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(() -> new AucException(AucMessage.BRAND_NOT_FOUND.getCode(), AucMessage.BRAND_NOT_FOUND.getMessage()));

            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(UUID.randomUUID().toString());
            productEntity.setAuction(auctionEntity);
            productEntity.setBrand(brandEntity);
            productEntity.setName(createProductRequest.getName());
            productEntity.setDescriptions(createProductRequest.getDescriptions());
            productEntity.setStartPrice(new BigDecimal(createProductRequest.getStartPrice()));
            productEntity.setMainImage(createProductRequest.getMainImage());
            productEntity.setImages(createProductRequest.getImages());
            productEntity.setSeries(createProductRequest.getSeries());
            productEntity.setCreatedDate(Instant.now());
            productEntity.setStatus(ActionStatus.CREATED.name());

            productEntity = repository.save(productEntity);
            if (ObjectUtils.isNotEmpty(productEntity)) {
                response.code(AucMessage.CREATE_PRODUCT_SUCCESS.getCode()).message(AucMessage.CREATE_PRODUCT_SUCCESS.getMessage())
                        .data(mapper.productEntityToProductItem(productEntity));
            }

        } catch (Exception ex) {
            log.error("[ProductServiceImpl.create] Exception when create product: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }


    @Override
    public CreateProductResponse update(UpdateProductRequest updateProductRequest) {
        CreateProductResponse response = new CreateProductResponse().code(AucMessage.UPDATE_PRODUCT_FAILED.getCode()).message(AucMessage.UPDATE_PRODUCT_FAILED.getMessage());

        try {
            validate(updateProductRequest);
            ProductEntity productEntity = repository.findById(updateProductRequest.getId()).orElseThrow(() -> new AucException(AucMessage.PRODUCT_NOT_FOUND.getCode(), AucMessage.PRODUCT_NOT_FOUND.getMessage()));
            AuctionEntity auctionEntity = auctionRepository.findById(updateProductRequest.getAuctionId()).orElseThrow(() -> new AucException(AucMessage.AUCTION_NOT_FOUND.getCode(), AucMessage.AUCTION_NOT_FOUND.getMessage()));
            if (!auctionEntity.getUser().getId().equalsIgnoreCase(SecurityUtils.getCurrentUserId())) {
                throw new AucException(AucMessage.FORBIDDEN.getCode(), AucMessage.FORBIDDEN.getMessage());
            }
            if (!auctionEntity.getStatus().equalsIgnoreCase(AuctionStatus.PENDING.name()) || productEntity.getStatus().equals(ActionStatus.ACCEPTED.name())) {
                throw new AucException(AucMessage.CANNOT_UPDATE_PRODUCT.getCode(), AucMessage.CANNOT_UPDATE_PRODUCT.getMessage());
            }
            BrandEntity brandEntity = brandRepository.findById(updateProductRequest.getBrandId()).orElseThrow(() -> new AucException(AucMessage.BRAND_NOT_FOUND.getCode(), AucMessage.BRAND_NOT_FOUND.getMessage()));

            productEntity.setAuction(auctionEntity);
            productEntity.setBrand(brandEntity);
            productEntity.setName(updateProductRequest.getName());
            productEntity.setDescriptions(updateProductRequest.getDescriptions());
            productEntity.setStartPrice(new BigDecimal(updateProductRequest.getStartPrice()));
            productEntity.setMainImage(updateProductRequest.getMainImage());
            productEntity.setImages(updateProductRequest.getImages());
            productEntity.setSeries(updateProductRequest.getSeries());
            productEntity.setUpdatedDate(Instant.now());
            productEntity.setStatus(ActionStatus.UPDATED.name());

            productEntity = repository.save(productEntity);
            if (ObjectUtils.isNotEmpty(productEntity)) {
                response.code(AucMessage.UPDATE_PRODUCT_SUCCESS.getCode()).message(AucMessage.UPDATE_PRODUCT_SUCCESS.getMessage())
                        .data(mapper.productEntityToProductItem(productEntity));
            }

        } catch (Exception ex) {
            log.error("[ProductServiceImpl.create] Exception when update product: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    private void validate(UpdateProductRequest updateProductRequest) {
        if (!NumberUtils.isCreatable(updateProductRequest.getStartPrice()) || updateProductRequest.getImages().split(",").length > 4) {
            throw new AucException(AucMessage.BAD_REQUEST.getCode(), AucMessage.BAD_REQUEST.getMessage());
        }
    }

    private void validate(CreateProductRequest createProductRequest) {
        if (!NumberUtils.isCreatable(createProductRequest.getStartPrice()) || createProductRequest.getImages().split(",").length > 4) {
            throw new AucException(AucMessage.BAD_REQUEST.getCode(), AucMessage.BAD_REQUEST.getMessage());
        }
    }
}
