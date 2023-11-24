package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.Article;
import com.example.demo.repository.ArticleRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ArticleController.class})
@ExtendWith(SpringExtension.class)
class ArticleControllerTest {
    @Autowired
    private ArticleController articleController;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    void testGetArticlesBySection() throws Exception {
        when(articleRepository.findBySectionTitle(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/article/articles-by-section")
                .param("sectionTitle", "body");
        MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"pagination\":{\"total_page\":2,\"next_page\":2},\"data\":[]}"));
    }

    @Test
    void testGetArticlesBySection_isSuccess() throws Exception {
        Article article = new Article();
        article.setArticleTitle("articleTitle");
        article.setBody("The quick brown fox jumps over the lazy dog");
        article.setCreatedAt(LocalDate.of(2023, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        article.setCreatedBy("Jan 1, 2023 8:00am GMT+0100");
        article.setCreatedFrom("localhost");
        article.setId(1L);
        article.setModifiedAt(LocalDate.of(2023, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        article.setSectionTitle("articleTitle");
        article.setUpdatedBy("2023-03-01");
        article.setUpdatedDate("2023-03-01");
        article.setUpdatedFrom("localhost");

        ArrayList<Article> articleList = new ArrayList<>();
        articleList.add(article);
        when(articleRepository.findBySectionTitle(Mockito.<String>any())).thenReturn(articleList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/article/articles-by-section")
                .param("sectionTitle", "body");
        MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"pagination\":{\"total_page\":2,\"next_page\":2},\"data\":" +
                                        "[{\"sectionTitle\":\"body\",\"articleTitle\":\"articleTitle\",\"body\":\"" +
                                        "The quick brown fox jumps over the lazy dog\"}]}"));
    }
}

