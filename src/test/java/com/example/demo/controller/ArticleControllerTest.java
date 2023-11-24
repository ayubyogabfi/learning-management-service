package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    private ArticleService articleService;
    
    @Test
    void testSearchArticle() throws Exception {
        GeneralDataPaginationResponse.GeneralDataPaginationResponseBuilder<ArticleResponse> builderResult = GeneralDataPaginationResponse
                .builder();
        GeneralDataPaginationResponse.GeneralDataPaginationResponseBuilder<ArticleResponse> dataResult = builderResult
                .data(new ArrayList<>());
        GeneralDataPaginationResponse.GeneralDataPaginationResponseBuilder<ArticleResponse> filterResult = dataResult
                .filter(GeneralDataPaginationResponse.Filter.builder().sectionTitle("articleTitle").build());
        GeneralDataPaginationResponse.GeneralDataPaginationResponseBuilder<ArticleResponse> paginationResult = filterResult
                .pagination(GeneralDataPaginationResponse.Pagination.builder().nextPage(1).totalPage(1).build());
        when(articleService.searchArticle(Mockito.<SearchArticleRequest>any()))
                .thenReturn(paginationResult.sort(GeneralDataPaginationResponse.Sort.builder().order("Order").build()).build());

        SearchArticleRequest searchArticleRequest = new SearchArticleRequest();
        searchArticleRequest.setArticleTitle("articleTitle");
        String content = (new ObjectMapper()).writeValueAsString(searchArticleRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/article/search-article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"pagination\":{\"total_page\":1,\"next_page\":1},\"filter\"" +
                                        ":{\"section_title\":\"articleTitle\"},\"sort\":{\"order\":\"Order\""
                                        + "},\"data\":[]}"));
    }
}

