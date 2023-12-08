package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {

  @NotBlank(message = "Article title must not be empty")
  @Size(min = 3, message = "Article title must be at least 3 characters long")
  @JsonProperty("article_title")
  private String articleTitle;

  @JsonProperty("section_title")
  private String sectionTitle;

  @NotNull(message = "Article Section Id Must Not Null")
  @JsonProperty("article_section_id")
  private Long articleSectionId;

  @NotBlank(message = "Body must not be empty")
  @Size(min = 3, message = "Body must be at least 3 characters long")
  private String body;
}
