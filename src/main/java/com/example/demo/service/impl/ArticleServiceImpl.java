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
    Long sectionId = request.getSectionId();
    String sectionTitle = request.getSectionTitle();
    String articleTitle = request.getArticleTitle();

    // check article created by section id or section title
    if (sectionId != null && (sectionTitle == null || !sectionTitle.isEmpty())) {
      checkSectionId(sectionId, extractedUsername);
    } else if (sectionId == null && sectionTitle != null && !sectionTitle.isEmpty()) {
      sectionId = checkSectionTitle(sectionTitle, extractedUsername);
    } else if (sectionId == null && sectionTitle == null) {
      throw new BadRequestException("Please Input valid Section Id or Section Title");
    }

    //check article already on db or not
    checkArticle(request.getArticleTitle(), extractedUsername); //check article already on db or not

    // build new article
    Article newArticle = Article.builder().title(request.getArticleTitle()).body(request.getBody()).build();

    articleRepository.save(newArticle);

    // check article id
    Optional<Article> articles = articleRepository.findArticle(articleTitle, extractedUsername);
    assert articles.orElse(null) != null;
    Long articleIdFromDb = articles.orElse(null).getId();

    // save article to article section db
    ArticleSection articleSection = ArticleSection.builder().articleId(articleIdFromDb).sectionId(sectionId).build();
    articleSectionRepository.save(articleSection);

    // return response on creating article
    return CreateArticleResponse
      .builder()
      .articleTitle(request.getArticleTitle())
      .sectionTitle(request.getSectionTitle())
      .body(request.getBody())
      .build();
  }

  private void checkArticle(String articleTitle, String extractedUsername) {
    Optional<Article> articleSection = articleRepository.findArticleOnArticleSection(articleTitle, extractedUsername);
    // check article already on db or not
    if (articleSection.isPresent()) {
      throw new ConflictException("Article already exist");
    }
  }

  private void checkSectionId(Long sectionId, String extractedUsername) {
    List<Section> sectionById = sectionRepository.findSectionIdOnArticleSection(sectionId, extractedUsername);
    // check section by id already on db or not
    if (sectionById.isEmpty()) {
      throw new BadRequestException("Section not available on Database");
    }
  }

  private Long checkSectionTitle(String sectionTitle, String extractedUsername) {
    List<Section> sectionByTitle = sectionRepository.findSectionBySectionTitleAndUserLogin(
      sectionTitle,
      extractedUsername
    );
    // check section title already on db or not, if not, create section by title
    Long sectionId;
    if (sectionByTitle.isEmpty()) {
      Section createSection = Section.builder().title(sectionTitle).build();
      sectionRepository.save(createSection);

      Optional<Section> sections = sectionRepository.findSection(sectionTitle, extractedUsername);
      assert sections.orElse(null) != null;
      sectionId = sections.orElse(null).getId();
    } else {
      sectionId = sectionByTitle.get(0).getId(); // Assuming the list has only one element
    }
    return sectionId;
  }
}
