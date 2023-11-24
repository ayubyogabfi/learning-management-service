package com.example.demo.controller;

import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleListBySectionTitleRequest;
import com.example.demo.repository.ArticleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Get article by keyword", description = "Get article by keyword")
    @PostMapping("/search-article")
    public ResponseEntity<GeneralDataPaginationResponse<Article>> searchArticle(
            @Valid @RequestBody SearchArticleListBySectionTitleRequest request) {

        if (request.getSectionTitle() == null || request.getSectionTitle().trim().length() < 3) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GeneralDataPaginationResponse.<Article>builder()
                            .pagination(new GeneralDataPaginationResponse.Pagination(0, 0))
                            .data(null)
                            .build());
        }

        List<Article> articles = articleRepository.findByArticleTitle(request.getSectionTitle());

        GeneralDataPaginationResponse<Article> response = GeneralDataPaginationResponse.<Article>builder()
                .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
                .data(articles)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Get articles by section title", description = "Get articles by section title")
    @GetMapping("/articles-by-section")
    public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> getArticlesBySection(
            @RequestParam String sectionTitle) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = (authentication == null) ? null : authentication.getName();

        List<Article> articles = articleRepository.findBySectionTitle(sectionTitle);

        List<ArticleResponse> articleResponseDataList = articles.stream()
                .map(article -> new ArticleResponse(sectionTitle, article.getArticleTitle(), article.getBody()))
                .collect(Collectors.toList());

        GeneralDataPaginationResponse<ArticleResponse> response = GeneralDataPaginationResponse.<ArticleResponse>builder()
                .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
                .data(articleResponseDataList)
                .build();

        return ResponseEntity.ok(response);
    }
}