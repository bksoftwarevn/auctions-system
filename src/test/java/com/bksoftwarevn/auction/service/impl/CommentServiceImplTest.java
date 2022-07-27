package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.mapper.CommentMapper;
import com.bksoftwarevn.auction.mapper.LikeAuctionMapper;
import com.bksoftwarevn.auction.model.CommentsResponse;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.SearchCommentRequest;
import com.bksoftwarevn.auction.persistence.entity.AuctionEntity;
import com.bksoftwarevn.auction.persistence.entity.CommentEntity;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.AuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.CommentRepository;
import com.bksoftwarevn.auction.persistence.repository.LikeAuctionRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.service.CommonQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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

    }

    @Test
    void update() {
    }

    @Test
    void getAuctionsLiked() {
    }

    @Test
    void totalLiked() {
    }

    @Test
    void likeOrUnlike() {
    }
}