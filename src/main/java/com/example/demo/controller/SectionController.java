package com.example.demo.controller;

import com.example.demo.auth.JwtService;
import com.example.demo.dto.*;
import com.example.demo.entity.Section;
import com.example.demo.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/section")
public class SectionController {

  @Autowired
  private SectionService sectionService;

  @Autowired
  private JwtService jwtService;

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get all section",
    description = "Get all section list"
  )
  @GetMapping
  public ResponseEntity<GeneralDataPaginationResponse<SectionResponseData>> getAllSections(
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      List<Section> sections = sectionService.getAllSections(extractedUsername);

      List<SectionResponseData> sectionResponseDataList = sections
        .stream()
        .map(section -> SectionResponseData.builder().sectionTitle(section.title).build())
        .collect(Collectors.toList());

      GeneralDataPaginationResponse<SectionResponseData> response = GeneralDataPaginationResponse
        .<SectionResponseData>builder()
        .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
        .data(sectionResponseDataList)
        .build();

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Delete a section",
    description = "Delete a section"
  )
  @DeleteMapping
  public ResponseEntity<DeleteSectionResponse> deleteSection(
    @Valid @RequestBody DeleteSectionRequest request,
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    DeleteSectionResponse response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = sectionService.deleteSection(request, extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Create a section",
    description = "Create a section"
  )
  @PostMapping
  public ResponseEntity<CreateSectionResponse> createSection(
    @Valid @RequestBody CreateSectionRequest request,
    @RequestHeader("Authorization") String authorizationHeader
  ) {
    CreateSectionResponse response;
    try {
      String extractedToken = jwtService.extractBearerToken(authorizationHeader);

      if (extractedToken == null || extractedToken.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      String extractedUsername = jwtService.extractUsername(extractedToken);

      if (extractedUsername == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      response = sectionService.createSection(request, extractedUsername);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok(response);
  }
}
