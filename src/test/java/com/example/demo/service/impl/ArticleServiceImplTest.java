package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.repository.ArticleRepository;
import java.util.ArrayList;
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

  @Test
  void testSearchArticle() {
    when(articleRepository.findArticleByKeyword(Mockito.<String>any())).thenReturn(new ArrayList<>());
    GeneralDataPaginationResponse<ArticleResponse> actualSearchArticleResult = articleServiceImpl.searchArticle(
      new SearchArticleRequest("Keyword")
    );
    assertTrue(actualSearchArticleResult.getData().isEmpty());
    assertNull(actualSearchArticleResult.getSort());
    assertNull(actualSearchArticleResult.getFilter());
    GeneralDataPaginationResponse.Pagination pagination = actualSearchArticleResult.getPagination();
    assertEquals(2, pagination.getTotalPage());
    assertEquals(2, pagination.getNextPage());
    verify(articleRepository).findArticleByKeyword(Mockito.<String>any());
  }

  @Test
  void testSearchArticle_nullKeyword() {
    GeneralDataPaginationResponse<ArticleResponse> actualSearchArticleResult = articleServiceImpl.searchArticle(
      new SearchArticleRequest(null)
    );
    assertNull(actualSearchArticleResult.getData());
    assertNull(actualSearchArticleResult.getSort());
    assertNull(actualSearchArticleResult.getFilter());
    GeneralDataPaginationResponse.Pagination pagination = actualSearchArticleResult.getPagination();
    assertEquals(0, pagination.getTotalPage());
    assertEquals(0, pagination.getNextPage());
  }
}
