package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.dto.PaginationDTO;
import com.bksoftwarevn.auction.dto.SearchDTO;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.NewsMapper;
import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.persistence.entity.NewsEntity;
import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntity;
import com.bksoftwarevn.auction.persistence.entity.NewsKnowledgeEntityId;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.NewsKnowledgeRepository;
import com.bksoftwarevn.auction.persistence.repository.NewsRepository;
import com.bksoftwarevn.auction.persistence.repository.UserRepository;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.CommonQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final NewsRepository repository = mock(NewsRepository.class);
    private final NewsMapper mapper = mock(NewsMapper.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CommonQueryService commonQueryService = mock(CommonQueryService.class);
    private final NewsKnowledgeRepository newsKnowledgeRepository = mock(NewsKnowledgeRepository.class);
    private final NewsServiceImpl newsService = new NewsServiceImpl(repository, mapper, userRepository, commonQueryService, newsKnowledgeRepository);

    @Test
    void search() {
        SearchNewsRequest searchNewsRequest = mock(SearchNewsRequest.class);

        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(NewsEntity.class, NewsEntity.class, searchDTO, null))
                .thenReturn(mock(PaginationDTO.class));

        SearchNewsResponse actualResult = newsService.search(searchNewsRequest);
        Assertions.assertNotNull(actualResult);
        searchDTOMockedStatic.close();
    }

    @Test
    void search1() {
        SearchNewsRequest searchNewsRequest = mock(SearchNewsRequest.class);

        MockedStatic<SearchDTO> searchDTOMockedStatic = mockStatic(SearchDTO.class);
        SearchDTO searchDTO = mock(SearchDTO.class);
        SearchDTO.SearchDTOBuilder searchDTOBuilder = mock(SearchDTO.SearchDTOBuilder.class);
        searchDTOMockedStatic.when(() -> SearchDTO.builder()).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.page(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.size(anyLong())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.orders(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.conditions(anyList())).thenReturn(searchDTOBuilder);
        when(searchDTOBuilder.build()).thenReturn(searchDTO);
        when(commonQueryService.search(NewsEntity.class, NewsEntity.class, searchDTO, null))
                .thenThrow(new AucException());

        SearchNewsResponse actualResult = newsService.search(searchNewsRequest);
        Assertions.assertNotNull(actualResult);
        searchDTOMockedStatic.close();
    }

    @Test
    void create() {
        CreateNewsRequest createNewsRequest = mock(CreateNewsRequest.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        CreateNewsResponse actualResult = newsService.create(createNewsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create_1() {
        CreateNewsRequest createNewsRequest = mock(CreateNewsRequest.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(NewsEntity.class))).thenReturn(null);


        CreateNewsResponse actualResult = newsService.create(createNewsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void create_2() {
        CreateNewsRequest createNewsRequest = mock(CreateNewsRequest.class);
        UserEntity userEntity = mock(UserEntity.class);

        MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getCurrentUserId()).thenReturn(MOCK_ID);
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(NewsEntity.class))).thenReturn(mock(NewsEntity.class));


        CreateNewsResponse actualResult = newsService.create(createNewsRequest);
        Assertions.assertNotNull(actualResult);
        mockedStatic.close();
    }

    @Test
    void delete() {
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());
        CommonResponse actualResult = newsService.delete(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void delete1() {
        NewsEntity newsEntity = mock(NewsEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        CommonResponse actualResult = newsService.delete(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update() {
        UpdateNewsRequest updateNewsRequest = mock(UpdateNewsRequest.class);

        when(updateNewsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateNewsResponse actualResult = newsService.update(updateNewsRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update1() {
        UpdateNewsRequest updateNewsRequest = mock(UpdateNewsRequest.class);

        when(updateNewsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(mock(NewsEntity.class)));
        when(repository.save(any(NewsEntity.class))).thenReturn(null);

        CreateNewsResponse actualResult = newsService.update(updateNewsRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void update2() {
        UpdateNewsRequest updateNewsRequest = mock(UpdateNewsRequest.class);

        when(updateNewsRequest.getId()).thenReturn(MOCK_ID);
        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(mock(NewsEntity.class)));
        when(repository.save(any(NewsEntity.class))).thenReturn(mock(NewsEntity.class));

        CreateNewsResponse actualResult = newsService.update(updateNewsRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void read() {
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = newsService.read(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void read1() {
        NewsEntity newsEntity = mock(NewsEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CommonResponse actualResult = newsService.read(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void read2() {
        NewsEntity newsEntity = mock(NewsEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(newsKnowledgeRepository.save(any(NewsKnowledgeEntity.class))).thenReturn(null);

        CommonResponse actualResult = newsService.read(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void read3() {
        NewsEntity newsEntity = mock(NewsEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(newsKnowledgeRepository.save(any(NewsKnowledgeEntity.class))).thenReturn(mock(NewsKnowledgeEntity.class));

        CommonResponse actualResult = newsService.read(MOCK_ID, MOCK_ID);

        Assertions.assertNotNull(actualResult);
    }

    @Test
    void detail() {
        when(repository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateNewsResponse actualResult = newsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void detail1() {
        NewsEntity newsEntity = mock(NewsEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.empty());

        CreateNewsResponse actualResult = newsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void detail2() {
        NewsEntity newsEntity = mock(NewsEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(newsKnowledgeRepository.findById(any(NewsKnowledgeEntityId.class))).thenReturn(Optional.empty());

        CreateNewsResponse actualResult = newsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void detail3() {
        NewsEntity newsEntity = mock(NewsEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        NewsKnowledgeEntity entity = mock(NewsKnowledgeEntity.class);
        NewsItem newsItem = mock(NewsItem.class);

        when(repository.findById(MOCK_ID)).thenReturn(Optional.of(newsEntity));
        when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(userEntity));
        when(newsKnowledgeRepository.findById(any(NewsKnowledgeEntityId.class))).thenReturn(Optional.of(entity));
        when(mapper.mappingEntityToItem(newsEntity)).thenReturn(newsItem);

        CreateNewsResponse actualResult = newsService.detail(MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }
}