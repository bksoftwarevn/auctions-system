package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.model.CreateNewsRequest;
import com.bksoftwarevn.auction.model.CreateNewsResponse;
import com.bksoftwarevn.auction.model.SearchNewsRequest;
import com.bksoftwarevn.auction.model.SearchNewsResponse;
import com.bksoftwarevn.auction.model.UpdateNewsRequest;

public interface NewsService {
    SearchNewsResponse search(SearchNewsRequest searchNewsRequest);

    CreateNewsResponse create(CreateNewsRequest createNewsRequest);

    com.bksoftwarevn.auction.model.CommonResponse delete(String id);

    CreateNewsResponse update(UpdateNewsRequest updateNewsRequest);

    com.bksoftwarevn.auction.model.CommonResponse read(String currentUserId, String newsId);

    CreateNewsResponse detail(String currentUserId, String id);
}
