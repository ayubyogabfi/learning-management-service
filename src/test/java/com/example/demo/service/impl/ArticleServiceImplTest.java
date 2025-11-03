package com.example.demo.service.impl;

import com.example.demo.dto.CreateArticleRequest;
import com.example.demo.dto.DeleteArticleRequest;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.dto.UpdateArticleRequest;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ArticleSectionRepository;
import com.example.demo.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ArticleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ArticleServiceImplTest {
    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleSectionRepository articleSectionRepository;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @MockBean
    private SectionRepository sectionRepository;

    @Test
    void testSearchArticle() {
        when(articleRepository.findArticleByKeyword(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        assertThrows(InternalError.class,
                () -> articleServiceImpl.searchArticle(SearchArticleRequest.builder().keyword("Keyword").build(), "username"));
        verify(articleRepository).findArticleByKeyword(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testFindAll() {
        when(articleRepository.findAllArticlesByExtractedUsername(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(InternalError.class, () -> articleServiceImpl.findAll("username"));
        verify(articleRepository).findAllArticlesByExtractedUsername(Mockito.<String>any());
    }

    @Test
    void testCreateArticle() {
        when(sectionRepository.findSectionIdOnArticleSection(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        assertThrows(InternalError.class,
                () -> articleServiceImpl.createArticle(CreateArticleRequest.builder()
                        .articleTitle("Article Title")
                        .body("Article Body Here")
                        .sectionId(1L)
                        .sectionTitle("Section Title")
                        .build(), "username"));
        verify(sectionRepository).findSectionIdOnArticleSection(Mockito.<Long>any(), Mockito.<String>any());
    }

    @Test
    void testUpdateArticle() {
        when(articleRepository.findArticleByArticleSectionId(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        assertThrows(InternalError.class,
                () -> articleServiceImpl.updateArticle(UpdateArticleRequest.builder()
                        .articleSectionId(1L)
                        .articleTitle("Article Title")
                        .body("Article Body Here")
                        .sectionTitle("Section Title")
                        .build(), "username"));
        verify(articleRepository).findArticleByArticleSectionId(Mockito.<Long>any(), Mockito.<String>any());
    }

    @Test
    void testDeleteArticle() {
        Article article = new Article();
        article.setBody("Article Body Here");
        article.setCreatedBy("Nov 11, 2023 8:00am GMT+0100");
        article.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
        article.setCreatedFrom("testemail@gmail.com");
        article.setDeletedDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
        article.setId(1L);
        article.setTitle("Section Title");
        article.setUpdatedBy("2023-11-11");
        article.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
        article.setUpdatedFrom("2023-11-11");
        doNothing().when(articleRepository).deleteArticle(Mockito.<Long>any(), Mockito.<String>any());
        when(articleRepository.findArticleByArticleId(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(article);
        doNothing().when(articleSectionRepository)
                .deleteArticleSectionByArticleId(Mockito.<Long>any(), Mockito.<String>any());

        DeleteArticleRequest request = new DeleteArticleRequest();
        request.setArticleId(1L);
        articleServiceImpl.deleteArticle(request, "username");
        verify(articleRepository).findArticleByArticleId(Mockito.<Long>any(), Mockito.<String>any());
        verify(articleRepository).deleteArticle(Mockito.<Long>any(), Mockito.<String>any());
        verify(articleSectionRepository).deleteArticleSectionByArticleId(Mockito.<Long>any(), Mockito.<String>any());
    }
    
}

