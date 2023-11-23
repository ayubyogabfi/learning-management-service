package com.example.demo.controller;


import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
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
    void testSearchArticle() throws Exception {
        SearchArticleRequest searchArticleRequest = new SearchArticleRequest();
        searchArticleRequest.setArticleTitle("Java");
        String content = (new ObjectMapper()).writeValueAsString(searchArticleRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/article/search-article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"pagination\":{\"total_page\":2,\"next_page\":2},\"data\":[]}"));
    }
}

