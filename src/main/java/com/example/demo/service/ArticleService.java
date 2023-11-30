package com.example.demo.service;

import com.example.demo.dto.*;

public interface ArticleService {
  GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request);

  GeneralDataPaginationResponse<ArticleResponse> findAll();

  CreateArticleResponse createArticle(CreateArticleRequest request);
}
