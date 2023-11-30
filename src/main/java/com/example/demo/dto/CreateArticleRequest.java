package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {

  @JsonProperty(value = "section_title")
  private String sectionTitle;

  @JsonProperty(value = "article_title")
  private String articleTitle;

  @JsonProperty(value = "body")
  private String body;
}
