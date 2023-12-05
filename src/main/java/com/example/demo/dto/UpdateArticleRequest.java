package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

  @NotBlank(message = "Body must not be empty")
  @Size(min = 3, message = "Body must be at least 3 characters long")
  private String body;

  @JsonProperty("user_id")
  private String userId;
}
