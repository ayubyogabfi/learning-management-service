package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleRequest {

  @NotEmpty(message = "Article title must not be empty")
  @JsonProperty(value = "article_title")
  private String articleTitle;
}
