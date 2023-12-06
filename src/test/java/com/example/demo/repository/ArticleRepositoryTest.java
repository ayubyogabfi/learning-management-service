//package com.example.demo.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import com.example.demo.dto.ArticleResponse;
//import com.example.demo.service.ArticleService;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class ArticleRepositoryTest {
//
//  @Mock
//  private ArticleRepository articleRepository;
//
//  @Test
//  void findArticleByKeyword_whenKeywordIsValid_ReturnsArticleResponseList() {
//    String articleTitle = "sample";
//    String extractedUsername = "user";
//    ArticleResponse articleResponse = new ArticleResponse("Section Title", "Sample Article", "Sample Body");
//    List<ArticleResponse> expectedResponse = List.of(articleResponse);
//
//    when(articleRepository.findArticleByKeyword(articleTitle, extractedUsername)).thenReturn(expectedResponse);
//
//    List<ArticleResponse> actualResponse = articleRepository.findArticleByKeyword(articleTitle, extractedUsername);
//
//    assertEquals(expectedResponse, actualResponse);
//  }
//}
