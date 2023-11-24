package com.example.demo.controller;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.entity.ArticleSection;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Get article by keyword",
            description = "Get article by keyword")
    @PostMapping("/learning-management-system/v1/article-list/{article-title}")
    public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> searchArticle(
            @Valid @RequestBody SearchArticleRequest request) {

        GeneralDataPaginationResponse<ArticleResponse> response = articleService.searchArticle(request);

        return ResponseEntity.ok(response);
    }
}
