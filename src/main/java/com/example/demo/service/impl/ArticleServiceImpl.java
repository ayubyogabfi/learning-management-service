package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.Article;
import com.example.demo.entity.Section;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CreateArticleRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.util.JwtUtil;
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
  private SectionRepository sectionRepository;

  @Autowired
  private CreateArticleRepository createArticleRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> searchArticle(SearchArticleRequest request) {
    String extractedUsername = JwtUtil.getSubject(request.getToken());

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
  public GeneralDataPaginationResponse<ArticleResponse> findAll(String token) {
    String extractedUsername = JwtUtil.getSubject(token);

    List<ArticleResponse> articles = articleRepository.findAllArticles(extractedUsername);

    return GeneralDataPaginationResponse
      .<ArticleResponse>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(articles)
      .build();
  }

  @Override
  public CreateArticleResponse createArticle(CreateArticleRequest request) {
    String extractedUsername = JwtUtil.getSubject(request.getToken()); // get username from token
    if (!request.getSectionId().isEmpty()) {
      checkSectionId(request.getSectionId(), extractedUsername);
    }
    if (!request.getSectionTitle().isEmpty()) {
      checkSectionTitle(request.getSectionTitle(), extractedUsername);
    }

    checkArticle(request.getArticleTitle(), extractedUsername); //check article already on db or not

    CreateArticleResponse newArticle = CreateArticleResponse.builder()
            .articleTitle(request.getArticleTitle())
            .body(request.getBody())
            .sectionTitle(request.getSectionTitle())
            .createdBy(extractedUsername)
            .createdFrom("localhost") // will be developed further
            .createdDate(LocalDateTime.now(ZoneId.systemDefault()))
            .build();

    return createArticleRepository.save(newArticle);
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

  private void checkSectionTitle(String sectionTitle, String extractedUsername) {
    List<Section> sectionByTitle = sectionRepository.findSectionTitleOnArticleSection(
      sectionTitle,
      extractedUsername
    );
    if (sectionByTitle.isEmpty()) {
      sectionRepository.createSectionBySectionTitle(sectionTitle, extractedUsername);
    }
  }
}
