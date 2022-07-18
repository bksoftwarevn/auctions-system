package com.bksoftwarevn.auction.service.impl;


import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.BrandMapper;
import com.bksoftwarevn.auction.model.BrandsResponse;
import com.bksoftwarevn.auction.model.CreateBrandRequest;
import com.bksoftwarevn.auction.model.CreateBrandResponse;
import com.bksoftwarevn.auction.model.UpdateBrandRequest;
import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import com.bksoftwarevn.auction.persistence.repository.BrandRepository;
import com.bksoftwarevn.auction.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repository;
    private final BrandMapper mapper;


    @Override
    public BrandsResponse pull() {
        BrandsResponse response = new BrandsResponse().code(AucMessage.PULL_BRAND_FAILED.getCode()).message(AucMessage.PULL_BRAND_FAILED.getMessage());

        try {
            List<BrandEntity> entities = repository.findAll();
            if (!CollectionUtils.isEmpty(entities)) {
                response.data(mapper.mappingItem(entities)).code(AucMessage.PULL_BRAND_SUCCESS.getCode()).message(AucMessage.PULL_BRAND_SUCCESS.getMessage());
            } else {
                response.code(AucMessage.ROLE_NOT_FOUND.getCode()).message(AucMessage.ROLE_NOT_FOUND.getMessage());
            }
        } catch (Exception ex) {
            log.error("[BrandServiceImpl.pull] Exception when get all brand: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CreateBrandResponse create(CreateBrandRequest createBrandRequest) {
        CreateBrandResponse response = new CreateBrandResponse().code(AucMessage.CREATE_BRAND_FAILED.getCode()).message(AucMessage.CREATE_BRAND_FAILED.getMessage());

        try {

            BrandEntity entity = new BrandEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setName(createBrandRequest.getName());
            entity.setInfo(createBrandRequest.getInfo());
            entity = repository.save(entity);
            if (ObjectUtils.isNotEmpty(entity)) {
                response.data(mapper.mappingEntityToItem(entity)).code(AucMessage.CREATE_BRAND_SUCCESS.getCode()).message(AucMessage.CREATE_BRAND_SUCCESS.getMessage());
            }
        } catch (Exception ex) {
            log.error("[BrandServiceImpl.create] Exception when create brand: ", ex);
            response.message(ex.getMessage());
        }
        return response;
    }

    @Override
    public CreateBrandResponse update(UpdateBrandRequest updateBrandRequest) {
        CreateBrandResponse response = new CreateBrandResponse().code(AucMessage.UPDATE_BRAND_FAILED.getCode()).message(AucMessage.UPDATE_BRAND_FAILED.getMessage());
        try {
            BrandEntity entity = repository.findById(updateBrandRequest.getId()).orElseThrow(() -> new AucException(AucMessage.ROLE_NOT_FOUND.getCode(), AucMessage.ROLE_NOT_FOUND.getMessage()));
            if (ObjectUtils.isNotEmpty(entity)) {
                entity.setName(updateBrandRequest.getName());
                entity.setInfo(updateBrandRequest.getInfo());
                entity = repository.save(entity);
                if (ObjectUtils.isNotEmpty(entity)) {
                    response.code(AucMessage.UPDATE_BRAND_SUCCESS.getCode()).message(AucMessage.UPDATE_BRAND_SUCCESS.getMessage())
                            .data(mapper.mappingEntityToItem(entity));
                }
            }
        } catch (Exception ex) {
            log.error("[BrandServiceImpl.update] Update role [{}] exception: ", updateBrandRequest, ex);
            response.message(ex.getMessage());
        }
        return response;
    }

}
