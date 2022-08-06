package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.*;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.CreateNewsRequest;
import com.bksoftwarevn.auction.model.CreateNewsResponse;
import com.bksoftwarevn.auction.model.SearchNewsRequest;
import com.bksoftwarevn.auction.model.SearchNewsResponse;
import com.bksoftwarevn.auction.model.UpdateNewsRequest;
import com.bksoftwarevn.auction.security.util.SecurityUtils;
import com.bksoftwarevn.auction.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class NewsController implements com.bksoftwarevn.auction.api.v1.NewsApi {

    private final NewsService newsService;

    @Override
    public ResponseEntity<SearchNewsResponse> getListNews(SearchNewsRequest searchNewsRequest) {
        log.info("[NewsController.getListNews] Start get list news with data: {}", searchNewsRequest);
        return ResponseEntity.ok(newsService.search(searchNewsRequest));
    }

    @Override
    public ResponseEntity<CreateNewsResponse> postCreateNews(CreateNewsRequest createNewsRequest) {
        log.info("[NewsController.postCreateNews] Start create news with data: {}", createNewsRequest);
        return ResponseEntity.ok(newsService.create(createNewsRequest));
    }

    @Override
    public ResponseEntity<CommonResponse> putDeleteNews(String id) {
        log.info("[NewsController.putDeleteNews] Start delete news with data: {}", id);
        return ResponseEntity.ok(newsService.delete(id));
    }

    @Override
    public ResponseEntity<CreateNewsResponse> putUpdateNews(UpdateNewsRequest updateNewsRequest) {
        log.info("[NewsController.putUpdateNews] Start get update news with data: {}", updateNewsRequest);
        return ResponseEntity.ok(newsService.update(updateNewsRequest));
    }

    @Override
    public ResponseEntity<CommonResponse> postReadNews(String id) {
        log.info("[ClientController.postReadNews] User read news [{}] request", id);
        return ResponseEntity.ok(newsService.read(SecurityUtils.getCurrentUserId(), id));
    }

    @Override
    public ResponseEntity<CreateNewsResponse> getNewDetail(String id) {
        log.info("[ClientController.getNewDetail] User read news detail [{}] request", id);
        return ResponseEntity.ok(newsService.detail(SecurityUtils.getCurrentUserId(), id));
    }
}
