package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.Article;
import com.example.demo.entity.ArticleSection;
import com.example.demo.entity.Section;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ArticleSectionRepository;
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

  @Autowired
  private ArticleSectionRepository articleSectionRepository;

  @Override
  public GeneralDataPaginationResponse<ArticleResponse> searchArticle(
    SearchArticleRequest request,
    String extractedUsername
  ) {
    SearchArticleRequest searchArticleRequest = SearchArticleRequest.builder().build();

    List<ArticleResponse> articles = articleRepository.findArticleByKeyword(
      searchArticleRequest.toString(),
      extractedUsername
    );

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
    String sectionId = request.getSectionId();
    String sectionTitle = request.getSectionTitle();
    String articleTitle = request.getArticleTitle();

    if (sectionId != null && (sectionTitle == null || !sectionTitle.isEmpty())) {
      checkSectionId(sectionId, extractedUsername);
    } else if (sectionId == null && sectionTitle != null && !sectionTitle.isEmpty()) {
      checkSectionTitle(sectionTitle, extractedUsername, articleTitle);
    } else if (sectionId == null && sectionTitle == null) {
      throw new BadRequestException("Please Input valid Section Id or Section Title");
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

  private void checkSectionTitle(String sectionTitle, String extractedUsername, String articleTitle) {
    List<Section> sectionByTitle = sectionRepository.findSectionBySectionTitleAndUserLogin(
      sectionTitle,
      extractedUsername
    );
    if (sectionByTitle.isEmpty()) {
      Section section = Section.builder().title(sectionTitle).build();
      sectionRepository.save(section);

      Optional<Article> articles = articleRepository.findArticleOnDatabase(articleTitle, extractedUsername);
      assert articles.orElse(null) != null;
      Long articleId = articles.orElse(null).getId();

      Optional<Section> sections = sectionRepository.findSectionOnDatabase(sectionTitle, extractedUsername);
      assert sections.orElse(null) != null;
      Long sectionId = sections.orElse(null).getId();

      ArticleSection articleSection = ArticleSection.builder().articleId(articleId).sectionId(sectionId).build();
      articleSectionRepository.save(articleSection);
    } else {
      throw new BadRequestException("Bad Request");
    }
  }
}
