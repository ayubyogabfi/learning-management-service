package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request) {
    if (request.getKeyword() == null || request.getKeyword().trim().length() < 3) {
      return GeneralDataPaginationResponse
        .<ArticleResponse>builder()
        .pagination(new GeneralDataPaginationResponse.Pagination(0, 0))
        .data(null)
        .build();
    }

    List<ArticleResponse> articles = articleRepository.findArticleByKeyword(request.getKeyword());

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> findAll() {
    List<ArticleResponse> articles = articleRepository.findAllArticles();

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }

  @Override
  public CreateArticleResponse createArticle(CreateArticleRequest request) {

    String sectionTitle = request.getSectionTitle();
    String articleTitle = request.getArticleTitle();
    String body = request.getBody();

  }

  private void checkArticle(String sectionTitle, String articleTitle, String body) {
    Optional<CreateArticleResponse> articleRequest = articleRepository.createArticle(
            sectionTitle, articleTitle, body
    );
  }


}
