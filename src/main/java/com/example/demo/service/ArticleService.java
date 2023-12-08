package com.example.demo.service;

import com.example.demo.dto.*;

public interface ArticleService {
  GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request, String extractedUsername);

  GeneralDataPaginationResponse<ArticleResponse> findAll(String extractedUsername);

  CreateArticleResponse createArticle(CreateArticleRequest request, String extractedUsername);
}
