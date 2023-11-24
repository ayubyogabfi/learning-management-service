package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleRequest {

  @NotEmpty(message = "Article title must not be empty")
  private String articleTitle;
}
