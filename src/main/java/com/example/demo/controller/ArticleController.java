package com.example.demo.controller;

import com.example.demo.dto.Article;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.repository.ArticleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Get article by keyword",
            description = "Get article by keyword")
    @PostMapping("/search-article")
    public ResponseEntity<GeneralDataPaginationResponse<Article>> searchArticle(
            @Valid @RequestBody SearchArticleRequest request) {

        if (request.getArticleTitle() == null || request.getArticleTitle().trim().length() < 3) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GeneralDataPaginationResponse.<Article>builder()
                            .pagination(new GeneralDataPaginationResponse.Pagination(0, 0))
                            .data(null)
                            .build());
        }

        List<Article> articles = articleRepository.findByArticleTitle(request.getArticleTitle());

        GeneralDataPaginationResponse<Article> response = GeneralDataPaginationResponse.<Article>builder()
                .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
                .data(articles)
                .build();

        return ResponseEntity.ok(response);
    }
}
