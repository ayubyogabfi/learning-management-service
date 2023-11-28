package com.example.demo.controller;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SearchArticleRequest;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    //          security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get all article (based on which user logged in)",
    description = "Get article list"
  )
  @GetMapping
  public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> findAllArticles() {
    GeneralDataPaginationResponse<ArticleResponse> response = articleService.findAll();
    return ResponseEntity.ok(response);
  }
}
