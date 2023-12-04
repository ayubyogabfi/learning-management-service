package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.CreateArticleRequest;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.entity.Article;
import com.example.demo.entity.Section;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CreateArticleRepository;
import com.example.demo.repository.SectionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { ArticleServiceImpl.class })
@ExtendWith(SpringExtension.class)
class ArticleServiceImplTest {

  @MockBean
  private ArticleRepository articleRepository;

  @Autowired
  private ArticleServiceImpl articleServiceImpl;

  @MockBean
  private CreateArticleRepository createArticleRepository;

  @MockBean
  private SectionRepository sectionRepository;

  @Test
  void testSearchArticle() {
    when(articleRepository.findArticleByKeyword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
    GeneralDataPaginationResponse<ArticleResponse> actualSearchArticleResult = articleServiceImpl.searchArticle(
      new SearchArticleRequest("Keyword"),
            "token"
    );
    assertTrue(actualSearchArticleResult.getData().isEmpty());
    assertNull(actualSearchArticleResult.getSort());
    assertNull(actualSearchArticleResult.getFilter());
    GeneralDataPaginationResponse.Pagination pagination = actualSearchArticleResult.getPagination();
    assertEquals(2, pagination.getTotalPage());
    assertEquals(2, pagination.getNextPage());
    verify(articleRepository).findArticleByKeyword(Mockito.<String>any(), Mockito.<String>any());
  }

  @Test
  void testFindAll() {
    when(articleRepository.findAllArticles(Mockito.<String>any())).thenReturn(new ArrayList<>());
    GeneralDataPaginationResponse<ArticleResponse> actualFindAllResult = articleServiceImpl.findAll(Mockito.<String>any());
    assertTrue(actualFindAllResult.getData().isEmpty());
    assertNull(actualFindAllResult.getSort());
    assertNull(actualFindAllResult.getFilter());
    GeneralDataPaginationResponse.Pagination pagination = actualFindAllResult.getPagination();
    assertEquals(2, pagination.getTotalPage());
    assertEquals(2, pagination.getNextPage());
    verify(articleRepository).findAllArticles(Mockito.<String>any());
  }

  @Test
  void testCreateArticle() {
    Article article = new Article();
    article.setCreatedBy("Nov 11, 2021 8:00am GMT+0100");
    article.setCreatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    article.setCreatedFrom("test.email@gmail.com");
    article.setDeletedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    article.setUpdatedBy("2022-11-11");
    article.setUpdatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    article.setUpdatedFrom("2022-11-11");
    Optional<Article> ofResult = Optional.of(article);
    when(articleRepository.findArticleOnDatabase(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    Section section = getSection();
    Optional<Section> ofResult2 = Optional.of(section);

    Section section2 = new Section();
    section2.setBody("this is article test");
    section2.setCreatedBy("Nov 11, 2021 8:00am GMT+0100");
    section2.setCreatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section2.setCreatedFrom("test.email@gmail.com");
    section2.setDeletedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section2.setId(1L);
    section2.setTitle("article_title");
    section2.setUpdatedBy("2022-11-11");
    section2.setUpdatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section2.setUpdatedFrom("2022-11-11");
    Optional<Section> ofResult3 = Optional.of(section2);
    when(sectionRepository.findSectionTitleOnArticleSection(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult3);
    when(sectionRepository.findSectionIdOnArticleSection(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    CreateArticleRequest request = new CreateArticleRequest();
    request.setArticleTitle("article_title");
    request.setBody("this is article test");
    request.setSectionId("42");
    request.setSectionTitle("article_title");
    assertThrows(ConflictException.class, () -> articleServiceImpl.createArticle(request, "token"));
    verify(articleRepository).findArticleOnDatabase(Mockito.<String>any(), Mockito.<String>any());
    verify(sectionRepository).findSectionIdOnArticleSection(Mockito.<String>any(), Mockito.<String>any());
    verify(sectionRepository).findSectionTitleOnArticleSection(Mockito.<String>any(), Mockito.<String>any());
  }

  private static Section getSection() {
    Section section = new Section();
    section.setBody("this is article test");
    section.setCreatedBy("Nov 11, 2021 8:00am GMT+0100");
    section.setCreatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section.setCreatedFrom("test.email@gmail.com");
    section.setDeletedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section.setId(1L);
    section.setTitle("article_title");
    section.setUpdatedBy("2022-11-11");
    section.setUpdatedDate(LocalDate.of(2023, 11, 11).atStartOfDay());
    section.setUpdatedFrom("2022-11-11");
    return section;
  }
}
