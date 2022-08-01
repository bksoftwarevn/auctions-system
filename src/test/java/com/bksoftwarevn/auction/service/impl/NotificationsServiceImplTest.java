package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionCategory;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.mapper.AuctionMapper;
import com.bksoftwarevn.auction.mapper.CategoryMapper;
import com.bksoftwarevn.auction.mapper.NotificationMapper;
import com.bksoftwarevn.auction.mapper.ProductMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.repository.NotificationRepository;
import com.bksoftwarevn.auction.persistence.repository.ProductRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.authorization.AuthoritiesConstants;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.CommonQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

class NotificationsServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";
    private static final String MOCK_ID_1 = "1f529f71-4c00-4acb-869b-a2808ecb23f8";
    private final NotificationRepository repository = mock(NotificationRepository.class);
    private final NotificationMapper mapper = mock(NotificationMapper.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CommonQueryService commonQueryService = mock(CommonQueryService.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductMapper productMapper = mock(ProductMapper.class);
    private final AuctionMapper auctionMapper = mock(AuctionMapper.class);
    private final CategoryMapper categoryMapper = mock(CategoryMapper.class);
    private final NotificationsServiceImpl notificationsService = new NotificationsServiceImpl(repository, mapper,
            userRepository, commonQueryService, productRepository, productMapper, auctionMapper, categoryMapper);

    @Test
    void test1_search() {
        SearchNotificationsRequest searchNotificationsRequest = mock(SearchNotificationsRequest.class);
        UserEntity userRequest = mock(UserEntity.class);
        PaginationDTO<NotificationEntity> notificationsEntityPaginationDTO = mock(PaginationDTO.class);

        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        when(commonQueryService.search(NotificationEntity.class, NotificationEntity.class, searchDTO, null))
                .thenReturn(notificationsEntityPaginationDTO);

        SearchNotificationsResponse actualResult = notificationsService.search(searchNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
        searchDTOMockedStatic.close();
    }

    @Test
    void test2_search() {
        SearchNotificationsRequest searchNotificationsRequest = mock(SearchNotificationsRequest.class);
        UserEntity userRequest = mock(UserEntity.class);
        PaginationDTO<NotificationEntity> notificationsEntityPaginationDTO = mock(PaginationDTO.class);

        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userRequest));
        when(commonQueryService.search(NotificationEntity.class, NotificationEntity.class, searchDTO, null))
                .thenReturn(notificationsEntityPaginationDTO);
        when(searchNotificationsRequest.getIsRead()).thenReturn(Boolean.TRUE);

        SearchNotificationsResponse actualResult = notificationsService.search(searchNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
        searchDTOMockedStatic.close();
    }

    @Test
    void test3_search() {
        SearchNotificationsRequest searchNotificationsRequest = mock(SearchNotificationsRequest.class);
        UserEntity userRequest = mock(UserEntity.class);
        PaginationDTO<NotificationEntity> notificationsEntityPaginationDTO = mock(PaginationDTO.class);

        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userRequest));
        when(commonQueryService.search(NotificationEntity.class, NotificationEntity.class, searchDTO, null))
                .thenReturn(notificationsEntityPaginationDTO);
        when(searchNotificationsRequest.getIsRead()).thenReturn(Boolean.FALSE);

        SearchNotificationsResponse actualResult = notificationsService.search(searchNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
        searchDTOMockedStatic.close();
    }

    @Test
    void test4_search() {
        SearchNotificationsRequest searchNotificationsRequest = mock(SearchNotificationsRequest.class);
        UserEntity userRequest = mock(UserEntity.class);
        Set<RoleEntity> roleEntitySet = new HashSet<>();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(AuthoritiesConstants.ROLE_ADMIN.name());
        roleEntitySet.add(roleEntity);
        PaginationDTO<NotificationEntity> notificationsEntityPaginationDTO = mock(PaginationDTO.class);

        when(userRequest.getRoles()).thenReturn(roleEntitySet);
        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userRequest));
        when(commonQueryService.search(NotificationEntity.class, NotificationEntity.class, searchDTO, null))
                .thenReturn(notificationsEntityPaginationDTO);
        when(searchNotificationsRequest.getIsRead()).thenReturn(null);

        SearchNotificationsResponse actualResult = notificationsService.search(searchNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
        searchDTOMockedStatic.close();
    }

    @Test
    void create() {
        CreateNotificationsRequest createNotificationsRequest = mock(CreateNotificationsRequest.class);

        MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class);
        securityUtilsMockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = notificationsService.create(createNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        securityUtilsMockedStatic.close();
    }

    @Test
    void create1() {
        CreateNotificationsRequest createNotificationsRequest = mock(CreateNotificationsRequest.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createNotificationsRequest.getReceiver()).thenReturn(MOCK_ID_1);
        MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class);
        securityUtilsMockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(userRepository.findById(MOCK_ID_1)).thenReturn(Optional.empty());

        CommonResponse actualResult = notificationsService.create(createNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        securityUtilsMockedStatic.close();
    }

    @Test
    void create2() {
        CreateNotificationsRequest createNotificationsRequest = mock(CreateNotificationsRequest.class);
        UserEntity userEntity = mock(UserEntity.class);
        NotificationEntity entity = mock(NotificationEntity.class);

        when(createNotificationsRequest.getReceiver()).thenReturn(MOCK_ID_1);
        MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class);
        securityUtilsMockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(userRepository.findById(MOCK_ID_1)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(NotificationEntity.class))).thenReturn(entity);

        CommonResponse actualResult = notificationsService.create(createNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        securityUtilsMockedStatic.close();
    }

    @Test
    void create3() {
        CreateNotificationsRequest createNotificationsRequest = mock(CreateNotificationsRequest.class);
        UserEntity userEntity = mock(UserEntity.class);
        NotificationEntity entity = mock(NotificationEntity.class);

        when(createNotificationsRequest.getReceiver()).thenReturn(MOCK_ID_1);
        MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class);
        securityUtilsMockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(userRepository.findById(MOCK_ID_1)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(NotificationEntity.class))).thenReturn(null);

        CommonResponse actualResult = notificationsService.create(createNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        securityUtilsMockedStatic.close();
    }

    @Test
    void delete() {
        CommonResponse actualResult = notificationsService.delete(MOCK_ID, MOCK_ID);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void delete1() {
        NotificationEntity entity = mock(NotificationEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));

        CommonResponse actualResult = notificationsService.delete(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update() {
        UpdateNotificationsRequest updateNotificationsRequest = mock(UpdateNotificationsRequest.class);

        when(updateNotificationsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = notificationsService.update(updateNotificationsRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update1() {
        UpdateNotificationsRequest updateNotificationsRequest = mock(UpdateNotificationsRequest.class);
        NotificationEntity entity = mock(NotificationEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(updateNotificationsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = notificationsService.update(updateNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update2() {
        UpdateNotificationsRequest updateNotificationsRequest = mock(UpdateNotificationsRequest.class);
        NotificationEntity entity = mock(NotificationEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(updateNotificationsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(entity.getUser()).thenReturn(new UserEntity());

        CommonResponse actualResult = notificationsService.update(updateNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void update3() {
        UpdateNotificationsRequest updateNotificationsRequest = mock(UpdateNotificationsRequest.class);
        NotificationEntity entity = mock(NotificationEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(updateNotificationsRequest.getId()).thenReturn(MOCK_ID);
        when(updateNotificationsRequest.getReceiver()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(entity.getUser()).thenReturn(userEntity);
        when(userRepository.findByUsername(MOCK_ID)).thenReturn(userEntity);
        when(userEntity.getUsername()).thenReturn(MOCK_ID);

        CommonResponse actualResult = notificationsService.update(updateNotificationsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void detail() {
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        DetailNotificationsResponse actualResponse = notificationsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void detail1() {
        NotificationEntity notificationEntity = mock(NotificationEntity.class);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(notificationEntity));

        DetailNotificationsResponse actualResponse = notificationsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void detail2() {
        NotificationEntity notificationEntity = mock(NotificationEntity.class);
        when(notificationEntity.getEventId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(notificationEntity));
        when(notificationEntity.getActionCategory()).thenReturn(ActionCategory.BID.name());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        DetailNotificationsResponse actualResponse = notificationsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void detail3() {
        NotificationEntity notificationEntity = mock(NotificationEntity.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(notificationEntity.getEventId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(notificationEntity));
        when(notificationEntity.getActionCategory()).thenReturn(ActionCategory.BID.name());
        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(productEntity));
        when(productEntity.getAuction()).thenReturn(auctionEntity);

        DetailNotificationsResponse actualResponse = notificationsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }
}