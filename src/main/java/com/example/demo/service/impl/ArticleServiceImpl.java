package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.Article;
import com.example.demo.entity.Section;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.SectionRepository;
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
  private SectionRepository sectionRepository;

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> searchArticle(
    SearchArticleRequest request,
    String extractedUsername
  ) {
    if (request.getKeyword() == null || request.getKeyword().trim().length() < 3) {
      return GeneralDataPaginationResponse
        .<ArticleResponse>builder()
        .pagination(new GeneralDataPaginationResponse.Pagination(0, 0))
        .data(null)
        .build();
    }

    List<ArticleResponse> articles = articleRepository.findArticleByKeyword(request.getKeyword(), extractedUsername);

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> findAll(String extractedUsername) {
    List<ArticleResponse> articles = articleRepository.findAllArticlesByUserLogin(extractedUsername);

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }

  @Override
  public CreateArticleResponse createArticle(CreateArticleRequest request, String extractedUsername) {
    if (!request.getSectionId().isEmpty()) {
      checkSectionId(request.getSectionId(), extractedUsername);
    }

    checkArticle(request.getArticleTitle(), extractedUsername); //check article already on db or not

    Article newArticle = Article.builder().title(request.getArticleTitle()).body(request.getBody()).build();

    articleRepository.save(newArticle);

    return CreateArticleResponse
      .builder()
      .articleTitle(request.getArticleTitle())
      .sectionTitle(request.getSectionTitle())
      .body(request.getBody())
      .build();
  }

  private void checkArticle(String articleTitle, String extractedUsername) {
    Optional<Article> articleSection = articleRepository.findArticleOnDatabase(articleTitle, extractedUsername);

    if (articleSection.isPresent()) {
      throw new ConflictException("Article already exist");
    }
  }

  private void checkSectionId(String sectionId, String extractedUsername) {
    List<Section> sectionById = sectionRepository.findSectionIdOnArticleSection(sectionId, extractedUsername);
    if (sectionById.isEmpty()) {
      throw new BadRequestException("Section not available on Database");
    }
  }
}
