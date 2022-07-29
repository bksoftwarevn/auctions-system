package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.mapper.CommentMapper;
import com.bksoftwarevn.auction.mapper.LikeAuctionMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.*;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.CommentRepository;
import com.bksoftwarevn.auction.persistence.repository.LikeAuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.CommonQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final CommentRepository repository = mock(CommentRepository.class);
    private final CommentMapper mapper = mock(CommentMapper.class);
    private final AuctionRepository auctionRepository = mock(AuctionRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CommonQueryService commonQueryService = mock(CommonQueryService.class);
    private final LikeAuctionRepository likeAuctionRepository = mock(LikeAuctionRepository.class);
    private final LikeAuctionMapper likeAuctionMapper = mock(LikeAuctionMapper.class);
    private CommentServiceImpl commentService = new CommentServiceImpl(repository, mapper, auctionRepository, userRepository,
            commonQueryService, likeAuctionRepository, likeAuctionMapper);

    @Test
    void give_delete() {

        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());
        CommonResponse actualResult = commentService.delete(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_delete() {
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("1234");

        CommonResponse actualResult = commentService.delete(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_delete() {
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(entity.getId()).thenReturn(MOCK_ID);
        when(repository.deleteByIdAndUserId(MOCK_ID, MOCK_ID)).thenReturn(-1l);

        CommonResponse actualResult = commentService.delete(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_delete() {
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));
        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(entity.getId()).thenReturn(MOCK_ID);
        when(repository.deleteByIdAndUserId(MOCK_ID, MOCK_ID)).thenReturn(1l);

        CommonResponse actualResult = commentService.delete(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give_whenSearch() {
        SearchCommentRequest searchCommentRequest = mock(SearchCommentRequest.class);

        when(searchCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommentsResponse actualResult = commentService.search(searchCommentRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenSearch() {
        SearchCommentRequest searchCommentRequest = mock(SearchCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        PaginationDTO<CommentEntity> auctionItemPaginationDTO = mock(PaginationDTO.class);

        when(searchCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(searchCommentRequest.getPage()).thenReturn(3);
        when(searchCommentRequest.getSize()).thenReturn(3);
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(CommentEntity.class, CommentEntity.class, searchDTO, null))
                .thenReturn(auctionItemPaginationDTO);

        CommentsResponse actualResult = commentService.search(searchCommentRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void give_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.empty());

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give4_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createCommentRequest.getParentId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give5_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        CommentEntity commentEntity = mock(CommentEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createCommentRequest.getParentId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(commentEntity));
        when(repository.save(any(CommentEntity.class))).thenReturn(commentEntity);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give6_whenCreate() {
        CreateCommentRequest createCommentRequest = mock(CreateCommentRequest.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        CommentEntity commentEntity = mock(CommentEntity.class);

        when(createCommentRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(createCommentRequest.getParentId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(commentEntity));
        when(repository.save(any(CommentEntity.class))).thenReturn(null);
        when(auctionRepository.findByIdAndStatus(MOCK_ID, AuctionStatus.STARTING.name())).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));

        CreateCommentResponse actualResult = commentService.create(MOCK_ID, createCommentRequest);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give1_whenUpdate() {
        UpdateCommentRequest updateCommentRequest = mock(UpdateCommentRequest.class);

        when(updateCommentRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateCommentResponse expectedResult = commentService.update(MOCK_ID, updateCommentRequest);

        Assertions.assertNotNull(expectedResult);
    }

    @Test
    void give2_whenUpdate() {
        UpdateCommentRequest updateCommentRequest = mock(UpdateCommentRequest.class);
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn("123");
        when(updateCommentRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));

        CreateCommentResponse expectedResult = commentService.update(MOCK_ID, updateCommentRequest);

        Assertions.assertNotNull(expectedResult);
    }

    @Test
    void give3_whenUpdate() {
        UpdateCommentRequest updateCommentRequest = mock(UpdateCommentRequest.class);
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(updateCommentRequest.getId()).thenReturn(MOCK_ID);
        when(repository.save(any(CommentEntity.class))).thenReturn(null);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));

        CreateCommentResponse expectedResult = commentService.update(MOCK_ID, updateCommentRequest);

        Assertions.assertNotNull(expectedResult);
    }

    @Test
    void give4_whenUpdate() {
        UpdateCommentRequest updateCommentRequest = mock(UpdateCommentRequest.class);
        CommentEntity entity = mock(CommentEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(entity.getUser()).thenReturn(userEntity);
        when(userEntity.getId()).thenReturn(MOCK_ID);
        when(updateCommentRequest.getId()).thenReturn(MOCK_ID);
        when(repository.save(any(CommentEntity.class))).thenReturn(entity);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(entity));

        CreateCommentResponse expectedResult = commentService.update(MOCK_ID, updateCommentRequest);

        Assertions.assertNotNull(expectedResult);
    }

    @Test
    void give1_getAuctionsLiked() {
        SearchAuctionLikedRequest searchAuctionLikedRequest = mock(SearchAuctionLikedRequest.class);

        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        SearchAuctionsLikedResponse actualResponse = commentService.getAuctionsLiked(MOCK_ID, searchAuctionLikedRequest);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void give2_getAuctionsLiked() {
        SearchAuctionLikedRequest searchAuctionLikedRequest = mock(SearchAuctionLikedRequest.class);
        UserEntity userEntity = mock(UserEntity.class);
        PaginationDTO paginationDTO = mock(PaginationDTO.class);
        List<LikeAuctionEntity> items = new ArrayList<>();
        LikeAuctionEntity auctionEntity = new LikeAuctionEntity();
        auctionEntity.setIsLiked(true);
        items.add(auctionEntity);
        AuctionLikedItem auctionLikedItem = mock(AuctionLikedItem.class);

        MockedStatic<SearchDTO> mockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        mockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);

        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(commonQueryService.search(LikeAuctionEntity.class, LikeAuctionEntity.class, searchDTO, null))
                .thenReturn(paginationDTO);
        when(paginationDTO.getItems()).thenReturn(items);
        when(likeAuctionMapper.mappingAuctionToItem(any(LikeAuctionEntity.class))).thenReturn(auctionLikedItem);

        SearchAuctionsLikedResponse actualResponse = commentService.getAuctionsLiked(MOCK_ID, searchAuctionLikedRequest);
        Assertions.assertNotNull(actualResponse);
        mockedStatic.close();
    }

    @Test
    void give1_totalLiked() {
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        TotalLikeResponse actualResponse = commentService.totalLiked(MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void give2_totalLiked() {
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));

        TotalLikeResponse actualResponse = commentService.totalLiked(MOCK_ID);
        Assertions.assertNotNull(actualResponse);
    }

    @Test
    void give1_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.empty());

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give2_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give3_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give4_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.REJECTED.name());

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give5_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.PENDING.name());

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give6_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give7_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(likeAuctionRepository.save(any(LikeAuctionEntity.class))).thenReturn(likeAuctionEntity);
        when(likeAuctionEntity.getIsLiked()).thenReturn(false);

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give8_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(likeAuctionRepository.save(any(LikeAuctionEntity.class))).thenReturn(null);
        when(likeAuctionEntity.getIsLiked()).thenReturn(null);

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void give9_whenLikeOrUnlike() {
        CreateLikeRequest createLikeRequest = mock(CreateLikeRequest.class);
        LikeAuctionEntity likeAuctionEntity = mock(LikeAuctionEntity.class);
        AuctionEntity auctionEntity = mock(AuctionEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(createLikeRequest.getAuctionId()).thenReturn(MOCK_ID);
        when(likeAuctionRepository.findById(any(LikeAuctionEntityId.class))).thenReturn(Optional.of(likeAuctionEntity));
        when(auctionRepository.findById(MOCK_ID)).thenReturn(Optional.of(auctionEntity));
        when(auctionEntity.getStatus()).thenReturn(AuctionStatus.WAITING.name());
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(likeAuctionRepository.save(any(LikeAuctionEntity.class))).thenReturn(null);
        when(likeAuctionEntity.getIsLiked()).thenReturn(true);

        CreateLikeResponse actualResult = commentService.likeOrUnlike(MOCK_ID, createLikeRequest);
        Assertions.assertNotNull(actualResult);
    }
}