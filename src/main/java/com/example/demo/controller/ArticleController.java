package com.example.demo.controller;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Get articles by section title", description = "Get articles by section title")
    @GetMapping("/article-list")
    public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> getArticlesBySection(
            @RequestParam String sectionTitle) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = (authentication == null) ? null : authentication.getName();

        GeneralDataPaginationResponse<ArticleResponse> response = articleService.getArticlesBySection(sectionTitle);

        return ResponseEntity.ok(response);
    }
}
