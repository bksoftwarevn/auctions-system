package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.mapper.ProductMapper;
import com.bksoftwarevn.auction.model.CreateProductRequest;
import com.bksoftwarevn.auction.model.CreateProductResponse;
import com.bksoftwarevn.auction.model.UpdateProductRequest;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.BrandEntity;
import com.bksoftwarevn.auction.persistence.entity.ProductEntity;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.BrandRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final ProductRepository repository = mock(ProductRepository.class);
    private final ProductMapper mapper = mock(ProductMapper.class);
    private final AuctionRepository auctionRepository = mock(AuctionRepository.class);
    private final BrandRepository brandRepository = mock(BrandRepository.class);
    private final ProductServiceImpl productService = new ProductServiceImpl(repository, mapper, auctionRepository, brandRepository);

    @Test
    void detail() {
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.detail(MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void detail2() {
        ProductEntity productEntity = mock(ProductEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));

        CreateProductResponse actualResult = productService.detail(MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);

        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123s");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create1() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);

        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212,232,3232");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create2() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);

        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void create3() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1332");

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create4() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create5() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(createProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create6() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(createProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(brandEntity));

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create7() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(createProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(brandEntity));
        when(repository.save(any(ProductEntity.class))).thenReturn(mock(ProductEntity.class));

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create8() {
        CreateProductRequest createProductRequest = mock(CreateProductRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(createProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createProductRequest.getStartPrice()).thenReturn("123");
        when(createProductRequest.getImages()).thenReturn("123,213,212");
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(createProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(brandEntity));
        when(repository.save(any(ProductEntity.class))).thenReturn(null);

        CreateProductResponse actualResult = productService.create(createProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);

        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123s");

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update2() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);

        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,3232,32323,32323,3232");

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update3() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update4() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update5() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn("123");

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update6() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update7() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(productEntity.getStatus()).thenReturn(ActionStatus.ACCEPTED.name());

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update8() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(productEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(updateProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update9() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(productEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(updateProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(brandEntity));
        when(repository.save(any(ProductEntity.class))).thenReturn(null);

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update10() {
        UpdateProductRequest updateProductRequest = mock(UpdateProductRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        when(updateProductRequest.getId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(updateProductRequest.getStartPrice()).thenReturn("123");
        when(updateProductRequest.getImages()).thenReturn("123,32323,3232");
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(auctionEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(productEntity.getStatus()).thenReturn(ActionStatus.PENDING.name());
        when(updateProductRequest.getBrandId()).thenReturn(MOCK_ID);
        when(brandRepository.findById(MOCK_ID)).thenReturn(Optional.of(brandEntity));
        when(repository.save(any(ProductEntity.class))).thenReturn(productEntity);

        CreateProductResponse actualResult = productService.update(updateProductRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }
}