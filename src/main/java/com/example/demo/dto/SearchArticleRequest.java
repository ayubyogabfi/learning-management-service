package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@Builder
public class SearchArticleRequest {

  @Size(min = 3, message = "Keyword must be at least 3 characters long")
  @NotEmpty(message = "Keyword must not be empty")
  @JsonProperty(value = "keyword")
  private String keyword;
}
