package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionStatus;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.BidMapper;
import com.bksoftwarevn.auction.mapper.BrandMapper;
import com.bksoftwarevn.auction.mapper.UserMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.AcceptBidRequest;
import com.bksoftwarevn.auction.model.AcceptBidResponse;
import com.bksoftwarevn.auction.model.AuctionItem;
import com.bksoftwarevn.auction.model.BidsResponse;
import com.bksoftwarevn.auction.model.CreateBidRequest;
import com.bksoftwarevn.auction.model.CreateBidResponse;
import com.bksoftwarevn.auction.model.SearchBidRequest;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.BidRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.CommonQueryService;
import com.bksoftwarevn.auction.service.EmailService;
import liquibase.pro.packaged.M;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

class BidServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final BidRepository repository = mock(BidRepository.class);
    private final BidMapper mapper = mock(BidMapper.class);
    private final AuctionMapper auctionMapper = mock(AuctionMapper.class);
    private final BrandMapper brandMapper = mock(BrandMapper.class);
    private final UserMapper userMapper = mock(UserMapper.class);

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AuctionRepository auctionRepository = mock(AuctionRepository.class);
    private final CommonQueryService commonQueryService = mock(CommonQueryService.class);
    private final EmailService emailService = mock(EmailService.class);

    private BidServiceImpl bidService;

    @BeforeEach
    void setup() {
        this.bidService = new BidServiceImpl(repository, mapper, auctionMapper, brandMapper, userMapper, productRepository,
                userRepository, auctionRepository, commonQueryService, emailService);
    }

    @Test
    void give_whenSearch_then() {
        SearchBidRequest searchBidRequest = mock(SearchBidRequest.class);

        when(searchBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        BidsResponse actualResult = bidService.search(searchBidRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenSearch_then() {
        SearchBidRequest searchBidRequest = mock(SearchBidRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(productEntity.getAuction()).thenReturn(auctionEntity);
        when(searchBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionEntity.getId()).thenReturn("123");

        BidsResponse actualResult = bidService.search(searchBidRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenSearch_then() {
        SearchBidRequest searchBidRequest = mock(SearchBidRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        PaginationDTO<BidEntity> bidEntityPaginationDTO = mock(PaginationDTO.class);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(productEntity.getAuction()).thenReturn(auctionEntity);
        when(searchBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(auctionEntity.getId()).thenReturn(MOCK_ID);
        when(commonQueryService.search(BidEntity.class, BidEntity.class, searchDTO, null)).thenReturn(bidEntityPaginationDTO);

        BidsResponse actualResult = bidService.search(searchBidRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);

        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.empty());

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234");

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("23212");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give4_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("2");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give5_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("232");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give6_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("232");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give7_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        Set<ProductEntity> productEntities = new HashSet<>();
        productEntities.add(productEntity);
        BidEntity bidEntity = mock(BidEntity.class);

        when(bidEntity.getProduct()).thenReturn(productEntity);
        when(bidEntity.getUser()).thenReturn(userEntity);
        when(productEntity.getId()).thenReturn(MOCK_ID);
        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("232");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(auctionEntity.getProducts()).thenReturn(productEntities);
        when(repository.save(any(BidEntity.class))).thenReturn(bidEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give8_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        Set<ProductEntity> productEntities = new HashSet<>();
        productEntities.add(productEntity);
        BidEntity bidEntity = mock(BidEntity.class);

        MockedStatic<ObjectUtils> mockedStatic = mockStatic(ObjectUtils.class);
        when(bidEntity.getProduct()).thenReturn(productEntity);
        when(bidEntity.getUser()).thenReturn(userEntity);
        when(productEntity.getId()).thenReturn(MOCK_ID);
        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("232");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.empty());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(auctionEntity.getProducts()).thenReturn(productEntities);
        when(repository.save(any(BidEntity.class))).thenReturn(bidEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        mockedStatic.when(() -> ObjectUtils.isNotEmpty(any(BidEntityId.class))).thenReturn(false);

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give9_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        BidEntity bidEntity = mock(BidEntity.class);
        Set<ProductEntity> productEntities = new HashSet<>();

        when(createBidRequest.getPrice()).thenReturn("12");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("12");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.of(bidEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(bidEntity.getPrice()).thenReturn(BigDecimal.TEN);
        when(auctionEntity.getProducts()).thenReturn(productEntities);
        when(bidEntity.getProduct()).thenReturn(productEntity);
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.valueOf(11l));

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give10_create_then() {
        CreateBidRequest createBidRequest = mock(CreateBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        BidEntity bidEntity = mock(BidEntity.class);
        Set<ProductEntity> productEntities = new HashSet<>();

        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(createBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234556");
        when(createBidRequest.getPrice()).thenReturn("1");
        when(repository.findById(any(BidEntityId.class))).thenReturn(Optional.of(bidEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(bidEntity.getPrice()).thenReturn(BigDecimal.TEN);
        when(auctionEntity.getProducts()).thenReturn(productEntities);
        when(bidEntity.getProduct()).thenReturn(productEntity);
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.valueOf(1l));

        CreateBidResponse actualResult = bidService.create(MOCK_ID, createBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.empty());

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getStatus()).thenReturn(ActionStatus.ACCEPTED.name());

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give4_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getStatus()).thenReturn(ActionStatus.CREATED.name());
        when(productEntity.getBuyer()).thenReturn(null);

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give５_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getStatus()).thenReturn(ActionStatus.CREATED.name());
        when(productEntity.getBuyer()).thenReturn("あああ");
        when(productEntity.getMaxBid()).thenReturn(null);

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give6_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getStatus()).thenReturn(ActionStatus.CREATED.name());
        when(productEntity.getBuyer()).thenReturn("あああ");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(productRepository.save(productEntity)).thenReturn(null);

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give7_whenAccept_then() {
        AcceptBidRequest acceptBidRequest = mock(AcceptBidRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionItem auctionItem = mock(AuctionItem.class);

        when(acceptBidRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(acceptBidRequest.getProductId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STOPPED.name())).thenReturn(Optional.of(auctionEntity));
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getStatus()).thenReturn(ActionStatus.CREATED.name());
        when(productEntity.getBuyer()).thenReturn("あああ");
        when(productEntity.getMaxBid()).thenReturn(BigDecimal.TEN);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(auctionMapper.mappingEntityToItem(auctionEntity)).thenReturn(auctionItem);
        when(productEntity.getAuction()).thenReturn(auctionEntity);
        when(auctionEntity.getUser()).thenReturn(mock(UserEntity.class));
        when(productEntity.getStartPrice()).thenReturn(BigDecimal.TEN);

        AcceptBidResponse actualResult = bidService.accept(acceptBidRequest);

        Assertions.assertNotNull(actualResult);
    }
}