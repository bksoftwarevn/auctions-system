package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.BrandMapper;
import com.bksoftwarevn.auction.model.BrandsResponse;
import com.bksoftwarevn.auction.model.CreateBrandRequest;
import com.bksoftwarevn.auction.model.CreateBrandResponse;
import com.bksoftwarevn.auction.model.UpdateBrandRequest;
import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import com.bksoftwarevn.auction.persistence.repository.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";
    private final BrandRepository brandRepository = mock(BrandRepository.class);
    private final BrandMapper brandMapper = mock(BrandMapper.class);
    private BrandServiceImpl brandService;

    @BeforeEach
    void setup() {
        brandService = new BrandServiceImpl(brandRepository, brandMapper);
    }

    @Test
    void give1_whenPull_then() {
        List<BrandEntity> entities = new ArrayList<>();

        when(brandRepository.findAll()).thenReturn(entities);

        BrandsResponse actualResult = brandService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_whenPull_then() {
        List<BrandEntity> entities = new ArrayList<>();
        entities.add(new BrandEntity());

        when(brandRepository.findAll()).thenReturn(entities);

        BrandsResponse actualResult = brandService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give4_whenPull_then() {
        List<BrandEntity> entities = new ArrayList<>();
        entities.add(new BrandEntity());

        when(brandRepository.findAll()).thenThrow(new AucException());

        BrandsResponse actualResult = brandService.pull();

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenCreate_then() {
        CreateBrandRequest createBrandRequest = mock(CreateBrandRequest.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        when(brandRepository.save(any(BrandEntity.class))).thenReturn(brandEntity);

        CreateBrandResponse actualResult = brandService.create(createBrandRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenCreate_then() {
        CreateBrandRequest createBrandRequest = mock(CreateBrandRequest.class);

        when(brandRepository.save(any(BrandEntity.class))).thenReturn(null);

        CreateBrandResponse actualResult = brandService.create(createBrandRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenCreate_then() {
        CreateBrandRequest createBrandRequest = mock(CreateBrandRequest.class);

        when(brandRepository.save(any(BrandEntity.class))).thenThrow(new AucException());

        CreateBrandResponse actualResult = brandService.create(createBrandRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenUpdate_then() {
        UpdateBrandRequest updateBrandRequest = mock(UpdateBrandRequest.class);

        when(updateBrandRequest.getId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateBrandResponse actualResult = brandService.update(updateBrandRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenUpdate_then() {
        UpdateBrandRequest updateBrandRequest = mock(UpdateBrandRequest.class);
        BrandEntity entity = mock(BrandEntity.class);

        when(updateBrandRequest.getId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(brandRepository.save(any(BrandEntity.class))).thenReturn(null);

        CreateBrandResponse actualResult = brandService.update(updateBrandRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenUpdate_then() {
        UpdateBrandRequest updateBrandRequest = mock(UpdateBrandRequest.class);
        BrandEntity entity = mock(BrandEntity.class);

        when(updateBrandRequest.getId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(brandRepository.save(any(BrandEntity.class))).thenReturn(entity);

        CreateBrandResponse actualResult = brandService.update(updateBrandRequest);

        Assertions.assertNotNull(actualResult);
    }
}