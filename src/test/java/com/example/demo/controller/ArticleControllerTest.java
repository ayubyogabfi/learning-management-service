package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.service.ArticleService;
import java.util.Collections;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @Test
    void getArticlesBySection() {
        // Mocking security context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mocking service response
        String sectionTitle = "Test Section";
        ArticleResponse articleResponse = new ArticleResponse("Test Section", "Test Article", "Test Body");
        GeneralDataPaginationResponse<ArticleResponse> expectedResponse = GeneralDataPaginationResponse
                .<ArticleResponse>builder()
                .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
                .data(Collections.singletonList(articleResponse))
                .build();
        when(articleService.getArticlesBySection(sectionTitle)).thenReturn(expectedResponse);

        // Call the controller method
        ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> responseEntity = articleController.getArticlesBySection(
                sectionTitle
        );

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
