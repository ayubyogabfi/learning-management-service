package com.example.demo.controller;

import com.example.demo.auth.JwtService;
import com.example.demo.dto.*;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @Autowired
  private JwtService jwtService;

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get article list (by user logged in)",
    description = "Get article list (by user logged in)"
  )
  @GetMapping("/v1/article-list")
  public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> findAllArticles(
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    GeneralDataPaginationResponse<ArticleResponse> response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = articleService.findAll(extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get article by keyword (by user logged in)",
    description = "Get article by keyword (by user logged in)"
  )
  @PostMapping(value = "/v1/article-list/{article-title}")
  public ResponseEntity<GeneralDataPaginationResponse<ArticleResponse>> searchArticle(
    @Valid @RequestBody SearchArticleRequest request,
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    GeneralDataPaginationResponse<ArticleResponse> response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = articleService.searchArticle(request, extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Create an article",
    description = "Create an article"
  )
  @PostMapping("/v1/create-article")
  public ResponseEntity<CreateArticleResponse> createArticle(
    @Valid @RequestBody CreateArticleRequest request,
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    CreateArticleResponse response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = articleService.createArticle(request, extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Update an article",
    description = "Update an article"
  )
  @PutMapping("/v1/update")
  public ResponseEntity<UpdateArticleResponse> updateArticle(
    @Valid @RequestBody UpdateArticleRequest request,
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    UpdateArticleResponse response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = articleService.updateArticle(request, extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }
}
