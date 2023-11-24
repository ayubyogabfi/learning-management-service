package com.example.demo.controller;

import com.example.demo.dto.GeneralDataPaginationResponse;
import com.example.demo.dto.SectionResponseData;
import com.example.demo.entity.Section;
import com.example.demo.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SectionController {

  private final SectionService sectionService;

  @Autowired
  public SectionController(SectionService sectionService) {
    this.sectionService = sectionService;
  }

  @Operation(
    security = { @SecurityRequirement(name = "bearer-key") },
    summary = "Get all section",
    description = "Get all section list"
  )
  @GetMapping("/v1/article-list")
  public ResponseEntity<GeneralDataPaginationResponse<SectionResponseData>> getAllSections() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String createdBy = (authentication == null) ? null : authentication.getName();

    List<Section> sections = sectionService.getAllSections();

    List<SectionResponseData> sectionResponseDataList = sections
      .stream()
      .map(section -> new SectionResponseData(section.getTitle(), section.getBody()))
      .collect(Collectors.toList());

    GeneralDataPaginationResponse<SectionResponseData> response = GeneralDataPaginationResponse
      .<SectionResponseData>builder()
      .pagination(new GeneralDataPaginationResponse.Pagination(2, 2))
      .data(sectionResponseDataList)
      .build();

    return ResponseEntity.ok(response);
  }
}
