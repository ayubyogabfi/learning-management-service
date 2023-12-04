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
    if (!request.getSectionId().isEmpty()) {
      checkSectionId(request.getSectionId(), request.getUserId());
    }
    if (!request.getSectionTitle().isEmpty()) {
      checkSectionTitle(request.getSectionTitle(), request.getUserId());
    }

    checkArticle(request.getArticleTitle(), request.getUserId()); //check article already on db or not

    CreateArticleResponse newArticle = new CreateArticleResponse();
    newArticle.setSectionTitle(request.getSectionTitle());
    newArticle.setArticleTitle(request.getArticleTitle());
    newArticle.setBody(request.getBody());
    newArticle.setCreatedBy(request.getUserId()); // will be developed further
    newArticle.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
    newArticle.setCreatedFrom("localhost"); // will be developed further

    return createArticleRepository.save(newArticle);
  }

  @Override
  public UpdateArticleResponse updateArticle(UpdateArticleRequest request) {
    if (!request.getSectionTitle().isEmpty()) {
      checkSectionTitle(request.getSectionTitle(), request.getUserId());
    }

    checkArticle(request.getArticleTitle(), request.getUserId());
    UpdateArticleResponse newArticle =  UpdateArticleResponse
            .builder()
            .articleTitle(request.getArticleTitle())
            .sectionTitle(request.getSectionTitle())
            .body(request.getBody())
            .updatedBy(request.getUserId())
            .updatedDate(LocalDateTime.now(ZoneId.systemDefault()))
            .updatedFrom("localhost")
            .build();

    return articleRepository.updateArticle(request);
  }

  private void checkArticle(String articleTitle, String userId) {
    Optional<Article> articleSection = articleRepository.findArticleOnDatabase(articleTitle, userId);

    if (articleSection.isPresent()) {
      throw new ConflictException("Article already exist");
    }
  }

  private void checkSectionId(String sectionId, String userId) {
    Optional<Section> sectionById = sectionRepository.findSectionIdOnArticleSection(sectionId, userId);
    if (sectionById.isEmpty()) {
      throw new BadRequestException("Section not available on Database");
    }
  }

  private void checkSectionTitle(String sectionTitle, String userId) {
    Optional<Section> sectionByTitle = sectionRepository.findSectionTitleOnArticleSection(sectionTitle, userId);
    if (sectionByTitle.isEmpty()) {
      sectionRepository.createSectionBySectionTitle(sectionTitle, userId);
    }
  }
}
