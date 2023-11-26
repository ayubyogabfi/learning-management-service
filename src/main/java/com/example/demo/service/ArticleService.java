package com.example.demo.service;

import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.repository.ArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;

  @Autowired
  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public GeneralDataPaginationResponse<ArticleResponse> getArticlesBySection(String sectionTitle) {
    List<Article> articles = articleRepository.findBySectionTitle(sectionTitle);

    List<ArticleResponse> articleResponseDataList = articles
      .stream()
      .map(article -> new ArticleResponse(sectionTitle, article.getArticleTitle(), article.getBody()))
      .collect(Collectors.toList());

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articleResponseDataList)
      .build();
  }
}
