package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.constant.SearchType;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.mapper.GroupMapper;
import com.bksoftwarevn.auction.mapper.UserMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.CategoryEntity;
import com.bksoftwarevn.auction.persistence.entity.GroupEntity;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.CategoryRepository;
import com.bksoftwarevn.auction.persistence.repository.GroupRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.CommonQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuctionServiceImplTest {

    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";
    private static final String MOCK_TYPE = "MOCK_TYPE";

    private AuctionRepository auctionRepository = mock(AuctionRepository.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final GroupRepository groupRepository = mock(GroupRepository.class);
    private final AuctionMapper mapper = mock(AuctionMapper.class);
    private final UserMapper userMapper = mock(UserMapper.class);
    private final CategoryMapper categoryMapper = mock(CategoryMapper.class);
    private final GroupMapper groupMapper = mock(GroupMapper.class);
    private final CommonQueryService commonQueryService = mock(CommonQueryService.class);

    @Test
    void giveDateNull_whenCreate_thenReturnError() {
        String userId = MOCK_ID;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        CreateAuctionRequest createAuctionRequest = mock(CreateAuctionRequest.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        Optional<CategoryEntity> optional = Optional.of(categoryEntity);
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(optional);
        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(userEntityOptional);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);


        CreateAuctionResponse actualResult = auctionService.create(createAuctionRequest, userId);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.VALIDATE_AUCTION_FAILED.getMessage(), actualResult.getMessage());
    }

    @Test
    void giveInValidUserId_whenCreate_thenReturnError() {
        String userId = MOCK_ID;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        CreateAuctionRequest createAuctionRequest = mock(CreateAuctionRequest.class);

        Mockito.when(createAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(createAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");

        CreateAuctionResponse actualResult = auctionService.create(createAuctionRequest, userId);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void giveInValidCategoryId_whenCreate_thenReturnError() {
        String userId = MOCK_ID;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        CreateAuctionRequest createAuctionRequest = mock(CreateAuctionRequest.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        Optional<CategoryEntity> categoryEntityOptional = Optional.of(categoryEntity);

        Mockito.when(createAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(categoryEntityOptional);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);
        Mockito.when(createAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(createAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");

        CreateAuctionResponse actualResult = auctionService.create(createAuctionRequest, userId);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void giveNullData_whenCreate_thenReturnError() {
        String userId = MOCK_ID;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        CreateAuctionRequest createAuctionRequest = mock(CreateAuctionRequest.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<CategoryEntity> categoryEntityOptional = Optional.of(categoryEntity);
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        Mockito.when(createAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(categoryEntityOptional);
        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(userEntityOptional);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(null);
        Mockito.when(createAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(createAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");

        CreateAuctionResponse actualResult = auctionService.create(createAuctionRequest, userId);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.CREATE_AUCTION_FAILED.getMessage(), actualResult.getMessage());
    }

    @Test
    void giveValidInput_whenCreate_thenSuccess() {
        String userId = MOCK_ID;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        CreateAuctionRequest createAuctionRequest = mock(CreateAuctionRequest.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        Optional<CategoryEntity> categoryEntityOptional = Optional.of(categoryEntity);
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        Mockito.when(createAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(categoryEntityOptional);
        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(userEntityOptional);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);
        Mockito.when(createAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(createAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");

        CreateAuctionResponse actualResult = auctionService.create(createAuctionRequest, userId);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.CREATE_AUCTION_SUCCESS.getCode(), actualResult.getCode());
        Assertions.assertEquals(AucMessage.CREATE_AUCTION_SUCCESS.getMessage(), actualResult.getMessage());
    }

    @Test
    void giveUserIdInvalid_whenPull_thenReturnError() {
        String userId = MOCK_ID;
        long page = 2;
        long size = 20;

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(null);

        AuctionsResponse actualResult = auctionService.pull(userId, page, size);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenPull_thenReturnError() {
        String userId = MOCK_ID;
        long page = 2;
        long size = 20;

        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        PaginationDTO<AuctionEntity> auctionEntityPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(optionalUserEntity);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionEntityPaginationDTO);

        AuctionsResponse actualResult = auctionService.pull(userId, page, size);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give1_whenPull_thenReturnError() {
        long page = 2;
        long size = 20;

        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        PaginationDTO<AuctionEntity> auctionEntityPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(optionalUserEntity);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionEntityPaginationDTO);

        AuctionsResponse actualResult = auctionService.pull(null, page, size);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give2_whenPull_thenReturnError() {
        long page = 2;
        long size = 20;

        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        PaginationDTO<AuctionEntity> auctionEntityPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        Mockito.when(userRepository.findById(eq(MOCK_ID))).thenReturn(Optional.empty());

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionEntityPaginationDTO);

        AuctionsResponse actualResult = auctionService.pull(MOCK_ID, page, size);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void giveDateNull_whenUpdate_thenThrowError() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.VALIDATE_AUCTION_FAILED.getMessage(), actualResult.getMessage());
        Assertions.assertEquals(AucMessage.VALIDATE_AUCTION_FAILED.getCode(), actualResult.getCode());
    }

    @Test
    void giveInvalidAuctionId_whenUpdate_thenThrowError() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);

        when(updateAuctionRequest.getId()).thenReturn(MOCK_ID);
        Mockito.when(updateAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(updateAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void giveWrongStatus_whenUpdate_thenThrowError() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);
        Optional<AuctionEntity> auctionEntityOptional = Optional.of(auctionEntity);

        when(updateAuctionRequest.getId()).thenReturn(MOCK_ID);
        Mockito.when(updateAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(updateAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");
        Mockito.when(auctionRepository.findById(eq(MOCK_ID))).thenReturn(auctionEntityOptional);
        Mockito.when(auctionEntity.getStatus()).thenReturn(AuctionStatus.REJECTED.name());

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.CANNOT_UPDATE_AUCTION.getMessage(), actualResult.getMessage());
        Assertions.assertEquals(AucMessage.CANNOT_UPDATE_AUCTION.getCode(), actualResult.getCode());
    }

    @Test
    void giveCategoryIdNotFound_whenUpdate_thenThrowError() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);
        Optional<AuctionEntity> auctionEntityOptional = Optional.of(auctionEntity);

        when(updateAuctionRequest.getId()).thenReturn(MOCK_ID);
        Mockito.when(updateAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(updateAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");
        Mockito.when(auctionRepository.findById(eq(MOCK_ID))).thenReturn(auctionEntityOptional);
        Mockito.when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void giveInValid_whenUpdate_thenSuccess() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);
        Optional<AuctionEntity> auctionEntityOptional = Optional.of(auctionEntity);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        Optional<CategoryEntity> optionalCategoryEntity = Optional.of(categoryEntity);

        when(updateAuctionRequest.getId()).thenReturn(MOCK_ID);
        when(updateAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        Mockito.when(updateAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(updateAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");
        Mockito.when(auctionRepository.findById(eq(MOCK_ID))).thenReturn(auctionEntityOptional);
        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(optionalCategoryEntity);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(null);
        Mockito.when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void giveValidInput_whenUpdate_thenSuccess() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UpdateAuctionRequest updateAuctionRequest = mock(UpdateAuctionRequest.class);
        Optional<AuctionEntity> auctionEntityOptional = Optional.of(auctionEntity);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);
        Optional<CategoryEntity> optionalCategoryEntity = Optional.of(categoryEntity);

        when(updateAuctionRequest.getId()).thenReturn(MOCK_ID);
        when(updateAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        Mockito.when(updateAuctionRequest.getEndDate()).thenReturn("21/20/2022 15:05:33");
        Mockito.when(updateAuctionRequest.getStartDate()).thenReturn("21/20/2022 13:05:33");
        Mockito.when(auctionRepository.findById(eq(MOCK_ID))).thenReturn(auctionEntityOptional);
        Mockito.when(categoryRepository.findById(eq(MOCK_ID))).thenReturn(optionalCategoryEntity);
        Mockito.when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);
        Mockito.when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CreateAuctionResponse actualResult = auctionService.update(updateAuctionRequest);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(AucMessage.CREATE_AUCTION_SUCCESS.getMessage(), actualResult.getMessage());
        Assertions.assertEquals(AucMessage.CREATE_AUCTION_SUCCESS.getCode(), actualResult.getCode());
    }

    @Test
    void give_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);

        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.getData());
        Assertions.assertEquals(auctionItems, actualResult.getData().getAuctions());
        mockedStatic.close();
    }

    @Test
    void give1_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);

        when(filterAuctionRequest.getStatus()).thenReturn("1234");
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.getData());
        Assertions.assertEquals(auctionItems, actualResult.getData().getAuctions());
        mockedStatic.close();
    }

    @Test
    void give2_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);

        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(optionalUserEntity);
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.getData());
        Assertions.assertEquals(auctionItems, actualResult.getData().getAuctions());
        mockedStatic.close();
    }

    @Test
    void give3_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);

        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give4_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(groupRepository.findByType(MOCK_TYPE)).thenReturn(null);
        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getType()).thenReturn(MOCK_TYPE);
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(optionalUserEntity);
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give5_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(groupRepository.findByType(MOCK_TYPE)).thenReturn(groupEntity);
        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getType()).thenReturn(MOCK_TYPE);
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(optionalUserEntity);
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give6_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);
        GroupEntity groupEntity = mock(GroupEntity.class);

        when(groupRepository.findByType(MOCK_TYPE)).thenReturn(groupEntity);
        when(categoryRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        when(filterAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getType()).thenReturn(MOCK_TYPE);
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(optionalUserEntity);
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give7_whenFilterAuctions_thenThrowException() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        FilterAuctionRequest filterAuctionRequest = mock(FilterAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);
        List<AuctionEntity> auctionEntityList = mock(List.class);
        List<AuctionItem> auctionItems = mock(List.class);
        UserEntity userEntity = mock(UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);
        UserDataItem userDataItem = mock(UserDataItem.class);
        GroupEntity groupEntity = mock(GroupEntity.class);
        Optional<CategoryEntity> optionalCategoryEntity = mock(Optional.class);

        when(groupRepository.findByType(MOCK_TYPE)).thenReturn(groupEntity);
        when(categoryRepository.findById(MOCK_ID)).thenReturn(optionalCategoryEntity);
        when(filterAuctionRequest.getCategoryId()).thenReturn(MOCK_ID);
        when(filterAuctionRequest.getStatus()).thenReturn(AuctionStatus.PENDING.name());
        when(filterAuctionRequest.getType()).thenReturn(MOCK_TYPE);
        when(filterAuctionRequest.getUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(optionalUserEntity);
        when(userMapper.mappingEntity(userEntity)).thenReturn(userDataItem);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(auctionItemPaginationDTO.getItems()).thenReturn(auctionEntityList);
        when(mapper.mappings(auctionEntityList)).thenReturn(auctionItems);

        Mockito.when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null)).thenReturn(auctionItemPaginationDTO);

        FilterAuctionResponse actualResult = auctionService.filterAuctions(filterAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give_whenDetail_then() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        DetailAuctionResponse actualResult = auctionService.detail(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenDetail_then() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        AuctionItem auctionItem = mock(AuctionItem.class);

        Optional<AuctionEntity> optionalAuctionEntity = Optional.of(auctionEntity);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(optionalAuctionEntity);
        when(mapper.mappingEntityToItem(auctionEntity)).thenReturn(auctionItem);

        DetailAuctionResponse actualResult = auctionService.detail(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenDetail_then() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        Optional<AuctionEntity> optionalAuctionEntity = Optional.of(auctionEntity);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(optionalAuctionEntity);
        when(mapper.mappingEntityToItem(auctionEntity)).thenThrow(new AucException());

        DetailAuctionResponse actualResult = auctionService.detail(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void delete() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);
        when(auctionRepository.deleteByIdAndUserIdAndStatus(MOCK_ID, MOCK_ID, AuctionStatus.PENDING.name())).thenReturn(1l);
        CommonResponse actualResult = auctionService.delete(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void delete1() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);
        when(auctionRepository.deleteByIdAndUserIdAndStatus(MOCK_ID, MOCK_ID, AuctionStatus.PENDING.name())).thenReturn(-1l);
        CommonResponse actualResult = auctionService.delete(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void delete2() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);
        when(auctionRepository.deleteByIdAndUserIdAndStatus(MOCK_ID, MOCK_ID, AuctionStatus.PENDING.name())).thenThrow(new AucException());
        CommonResponse actualResult = auctionService.delete(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void info() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(auctionRepository.findByIdAndUserId(MOCK_ID, MOCK_ID)).thenReturn(auctionEntity);
        InfoAuctionResponse actualResult = auctionService.info(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void info1() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        when(auctionRepository.findByIdAndUserId(MOCK_ID, MOCK_ID)).thenReturn(null);
        InfoAuctionResponse actualResult = auctionService.info(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void info2() {
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        when(auctionRepository.findByIdAndUserId(MOCK_ID, MOCK_ID)).thenThrow(new AucException());
        InfoAuctionResponse actualResult = auctionService.info(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenSearch_then() {
        SearchAuctionRequest searchAuctionRequest = mock(SearchAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null))
                .thenReturn(auctionItemPaginationDTO);

        SearchAuctionResponse actualResult = auctionService.search(searchAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void giveSearchType_whenSearch_then() {
        SearchAuctionRequest searchAuctionRequest = mock(SearchAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null))
                .thenReturn(auctionItemPaginationDTO);
        when(searchAuctionRequest.getType()).thenReturn(SearchType.START_PRICE.getType());
        when(searchAuctionRequest.getKeyword()).thenReturn("1234");

        SearchAuctionResponse actualResult = auctionService.search(searchAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void giveSearchType1_whenSearch_then() {
        SearchAuctionRequest searchAuctionRequest = mock(SearchAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null))
                .thenReturn(auctionItemPaginationDTO);
        when(searchAuctionRequest.getType()).thenReturn(SearchType.START_TIME.getType());
        when(searchAuctionRequest.getKeyword()).thenReturn("21/20/2022 15:05:33");
        when(searchAuctionRequest.getSize()).thenReturn(3l);
        when(searchAuctionRequest.getPage()).thenReturn(4l);

        SearchAuctionResponse actualResult = auctionService.search(searchAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void giveSearchType3_whenSearch_then() {
        SearchAuctionRequest searchAuctionRequest = mock(SearchAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null))
                .thenReturn(auctionItemPaginationDTO);
        when(searchAuctionRequest.getType()).thenReturn(SearchType.END_TIME.getType());
        when(searchAuctionRequest.getKeyword()).thenReturn("21/20/2022 15:05:33");
        when(searchAuctionRequest.getSize()).thenReturn(null);
        when(searchAuctionRequest.getPage()).thenReturn(null);

        SearchAuctionResponse actualResult = auctionService.search(searchAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void giveSearchType4_whenSearch_then() {
        SearchAuctionRequest searchAuctionRequest = mock(SearchAuctionRequest.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<AuctionEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(AuctionEntity.class, AuctionEntity.class, searchDTO, null))
                .thenThrow(new AucException());
        when(searchAuctionRequest.getType()).thenReturn(SearchType.END_TIME.getType());
        when(searchAuctionRequest.getKeyword()).thenReturn("21/20/2022 15:05:33");
        when(searchAuctionRequest.getSize()).thenReturn(null);
        when(searchAuctionRequest.getPage()).thenReturn(null);

        SearchAuctionResponse actualResult = auctionService.search(searchAuctionRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give_whenUpdateStatus_then() {
        UpdateAuctionStatusRequest updateAuctionStatusRequest = mock(UpdateAuctionStatusRequest.class);
        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        when(updateAuctionStatusRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = auctionService.updateStatus(updateAuctionStatusRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenUpdateStatus_then() {
        UpdateAuctionStatusRequest updateAuctionStatusRequest = mock(UpdateAuctionStatusRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        when(updateAuctionStatusRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CommonResponse actualResult = auctionService.updateStatus(updateAuctionStatusRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenUpdateStatus_then() {
        UpdateAuctionStatusRequest updateAuctionStatusRequest = mock(UpdateAuctionStatusRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.hasCurrentUserNoneOfAuthorities(eq(AuthoritiesConstants.ROLE_ADMIN.name()))).thenReturn(true);
        when(updateAuctionStatusRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());

        CommonResponse actualResult = auctionService.updateStatus(updateAuctionStatusRequest);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give3_whenUpdateStatus_then() {
        UpdateAuctionStatusRequest updateAuctionStatusRequest = mock(UpdateAuctionStatusRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.hasCurrentUserNoneOfAuthorities(eq(AuthoritiesConstants.ROLE_ADMIN.name()))).thenReturn(false);
        when(updateAuctionStatusRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(updateAuctionStatusRequest.getStatus()).thenReturn(AuctionStatus.DELIVERED.name());
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());

        CommonResponse actualResult = auctionService.updateStatus(updateAuctionStatusRequest);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give4_whenUpdateStatus_then() {
        UpdateAuctionStatusRequest updateAuctionStatusRequest = mock(UpdateAuctionStatusRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        AuctionServiceImpl auctionService = new AuctionServiceImpl(auctionRepository, categoryRepository,
                userRepository, groupRepository, mapper, userMapper, categoryMapper, groupMapper, commonQueryService);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.hasCurrentUserNoneOfAuthorities(eq(AuthoritiesConstants.ROLE_ADMIN.name()))).thenReturn(false);
        when(updateAuctionStatusRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(updateAuctionStatusRequest.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.DELIVERED.name());

        CommonResponse actualResult = auctionService.updateStatus(updateAuctionStatusRequest);

        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }
}