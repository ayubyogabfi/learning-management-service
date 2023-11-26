package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleListBySectionTitleRequest {

  @NotEmpty(message = "Section title must not be empty")
  private String sectionTitle;
}
