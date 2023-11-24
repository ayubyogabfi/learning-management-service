package com.example.demo.service;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;

  @Autowired
  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request) {
    if (request.getArticleTitle() == null || request.getArticleTitle().trim().length() < 3) {
      return GeneralDataPaginationResponse
        .<ArticleResponse>builder()
        .pagination(new GeneralDataPaginationResponse.Pagination(0, 0))
        .data(null)
        .build();
    }

    List<ArticleResponse> articles = articleRepository.findByArticleTitle(request.getArticleTitle());

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }
}
