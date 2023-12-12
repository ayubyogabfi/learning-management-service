package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.*;
import com.example.demo.entity.Article;
import com.example.demo.entity.ArticleSection;
import com.example.demo.entity.Section;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ArticleSectionRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.ArticleService;
import java.time.ZonedDateTime;
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
    if (sectionId != 0 && (sectionTitle == null || !sectionTitle.isEmpty())) {
      checkSectionId(sectionId, extractedUsername);
    } else if (sectionId == 0 && sectionTitle != null && !sectionTitle.isEmpty()) {
      sectionId = checkSectionTitle(sectionTitle, extractedUsername);
    } else if (sectionId == 0 && sectionTitle == null) {
      throw new BadRequestException("Please Input valid Section Id or Section Title");
    }

    //check article already on db or not
    checkArticle(request.getArticleTitle(), extractedUsername); //check article already on db or not

    // build new article
    Article newArticle = Article.builder().title(request.getArticleTitle()).body(request.getBody()).build();
    newArticle.setCreatedBy(extractedUsername);
    newArticle.setCreatedFrom(extractedUsername);
    newArticle.setCreatedDate(ZonedDateTime.now(AppConstants.ZONE_ID));

    articleRepository.save(newArticle);

    // check article id
    Article articles = articleRepository.findArticle(articleTitle, extractedUsername);
    Long articleId = articles.getId();

    // save article to article section db
    ArticleSection articleSection = ArticleSection.builder().articleId(articleId).sectionId(sectionId).build();

    articleSection.setCreatedBy(extractedUsername);
    articleSection.setCreatedFrom(extractedUsername);
    articleSection.setCreatedDate(ZonedDateTime.now(AppConstants.ZONE_ID));

    articleSectionRepository.save(articleSection);

    // return response on creating article
    return CreateArticleResponse
      .builder()
      .articleTitle(request.getArticleTitle())
      .sectionTitle(request.getSectionTitle())
      .body(request.getBody())
      .message("Article Successfully created")
      .build();
  }

  @Override
  public UpdateArticleResponse updateArticle(UpdateArticleRequest request, String extractedUsername) {
    Long articleSectionId = request.getArticleSectionId();
    String sectionTitle = request.getSectionTitle();
    String articleTitle = request.getArticleTitle();
    String body = request.getBody();

    // checking article section id already on db or not
    if (articleSectionId == 0) {
      throw new BadRequestException("Article Section Id Must Not Null");
    }

    // check if articleSectionId have content or not
    List<Article> articleSectionsList = articleRepository.findArticleByArticleSectionId(
      articleSectionId,
      extractedUsername
    );
    if (articleSectionsList.isEmpty()) {
      throw new InternalError("Article Section Id not available");
    }

    Long sectionId = checkSectionTitle(sectionTitle, extractedUsername);
    Long articleId = checkArticleId(articleSectionId, extractedUsername);

    articleSectionRepository.updateArticleSectionById(articleSectionId, sectionId, extractedUsername);

    articleRepository.updateArticle(articleId, articleTitle, body, extractedUsername);

    return UpdateArticleResponse
      .builder()
      .message("Article Successfully Updated")
      .articleTitle(articleTitle)
      .sectionTitle(sectionTitle)
      .body(body)
      .updatedDate(ZonedDateTime.now(AppConstants.ZONE_ID))
      .updatedBy(extractedUsername)
      .updatedFrom(extractedUsername)
      .build();
  }

  @Override
  public DeleteArticleResponse deleteArticle(DeleteArticleRequest request, String extractedUsername) {
    Article articles = articleRepository.findArticleByArticleId(request.getArticleId(), extractedUsername);

    if (articles == null || request.getArticleId() == 0) {
      throw new InternalError("No Article To Delete");
    }
    Long articleId = request.getArticleId();
    String articleTitle = articles.getTitle();
    String body = articles.getBody();

    articleRepository.deleteArticle(articleId, extractedUsername);

    articleSectionRepository.deleteArticleSectionByArticleId(articleId, extractedUsername);

    return DeleteArticleResponse
      .builder()
      .articleTitle(articleTitle)
      .body(body)
      .message("Article Successfully Deleted")
      .deletedDate(ZonedDateTime.now(AppConstants.ZONE_ID))
      .build();
  }

  private void checkArticle(String articleTitle, String extractedUsername) {
    Optional<Article> articleSection = articleRepository.findArticleOnArticleSection(articleTitle, extractedUsername);
    // check article already on db or not
    if (articleSection.isPresent()) {
      throw new InternalError("Article already exist");
    }
  }

  private Long checkArticleId(Long articleSectionId, String extractedUsername) {
    ArticleSection articleSection = articleSectionRepository.findOneByArticleSectionId(
      articleSectionId,
      extractedUsername
    );
    // find articleId
    return articleSection.getArticleId();
  }

  private void checkSectionId(Long sectionId, String extractedUsername) {
    List<Section> sectionById = sectionRepository.findSectionIdOnArticleSection(sectionId, extractedUsername);
    // check section by id already on db or not
    if (sectionById.isEmpty()) {
      throw new InternalError("Section not available on Database");
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
      createSection.setCreatedBy(extractedUsername);
      createSection.setCreatedFrom(extractedUsername);
      createSection.setCreatedDate(ZonedDateTime.now(AppConstants.ZONE_ID));
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
