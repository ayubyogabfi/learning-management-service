package com.example.demo.controller;

import com.example.demo.auth.JwtService;
import com.example.demo.dto.DeleteSectionRequest;
import com.example.demo.dto.DeleteSectionResponse;
import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SectionResponseData;
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
  @GetMapping("/v1/section-list")
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
    summary = "Delete an section",
    description = "Delete an section"
  )
  @DeleteMapping("/v1/delete-section")
  public ResponseEntity<DeleteSectionResponse> deleteArticle(
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
}
