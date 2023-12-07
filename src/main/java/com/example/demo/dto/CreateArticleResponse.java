package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleResponse {

  @JsonProperty(value = "section_title")
  private String sectionTitle;

  @JsonProperty(value = "article_title")
  private String articleTitle;

  @JsonProperty(value = "body")
  private String body;

  @CreationTimestamp
  @Column(name = "created_date", updatable = false, nullable = false)
  protected ZonedDateTime createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_from")
  private String createdFrom;
}
