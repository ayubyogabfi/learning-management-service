package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Article;

public interface ArticleService {
  GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request);

  GeneralDataPaginationResponse<ArticleResponse> findAll();

  Article createArticle(CreateArticleRequest request);
}
