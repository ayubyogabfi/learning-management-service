//package com.example.demo.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//import com.example.demo.dto.ArticleResponse;
//import com.example.demo.dto.GeneralDataPaginationResponse;
//import com.example.demo.dto.SearchArticleRequest;
//import com.example.demo.repository.ArticleRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = { ArticleService.class })
//@ExtendWith(SpringExtension.class)
//class ArticleServiceTest {
//
//  @MockBean
//  private ArticleRepository articleRepository;
//
//  @Autowired
//  private ArticleService articleService;
//
//  @Test
//  void testSearchArticle() {
//    GeneralDataPaginationResponse<ArticleResponse> actualSearchArticleResult = articleService.searchArticle(
//      new SearchArticleRequest("Dr")
//    );
//    assertNull(actualSearchArticleResult.getData());
//    assertNull(actualSearchArticleResult.getSort());
//    assertNull(actualSearchArticleResult.getFilter());
//    GeneralDataPaginationResponse.Pagination pagination = actualSearchArticleResult.getPagination();
//    assertEquals(0, pagination.getTotalPage());
//    assertEquals(0, pagination.getNextPage());
//  }
//}
