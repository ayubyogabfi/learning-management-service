package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/article")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get article list (by user logged in)",
    description = "Get article list (by user logged in)"
  )
  @PostMapping(value = "/{article-title}")
  public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> searchArticle(
    @Valid @RequestBody SearchArticleRequest request
  ) {
    GeneralDataPaginationResponse<ArticleResponse> response = articleService.searchArticle(request);

    return ResponseEntity.ok(response);
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Create an article",
    description = "Create an article"
  )
  @PostMapping("/create")
  public ResponseEntity<Article> createArticle(@Valid @RequestBody CreateArticleRequest request) {
    Article response = articleService.createArticle(request);

    return ResponseEntity.ok(response);
  }
}
