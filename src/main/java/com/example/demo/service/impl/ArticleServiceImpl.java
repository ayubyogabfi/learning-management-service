package com.example.demo.service.impl;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.CreateArticleRequest;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.entity.Article;
import com.example.demo.entity.ArticleSection;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
  public Article createArticle(CreateArticleRequest request) {
    checkArticle(request.getArticleTitle(), request.getSectionTitle());

    Article newArticle = new Article();
    newArticle.setSectionTitle(request.getSectionTitle());
    newArticle.setArticleTitle(request.getArticleTitle());
    newArticle.setBody(request.getBody());
    newArticle.setCreatedBy("user"); // will be developed further
    newArticle.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
    newArticle.setCreatedFrom("user"); // will be developed further

    return articleRepository.save(newArticle);
  }

  private void checkArticle(String articleTitle, String sectionTitle) {
    Optional<ArticleSection> articleSection = articleRepository.findArticleSectionByArticleTitleAndSectionTitle(
      articleTitle,
      sectionTitle
    );

    if (articleSection.isPresent()) {
      throw new ConflictException("Article already exist");
    }
  }
}
