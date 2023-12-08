package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleResponse {

  @JsonProperty(value = "section_title")
  private String sectionTitle;

  @JsonProperty(value = "article_title")
  private String articleTitle;

  @JsonProperty(value = "body")
  private String body;

  @CreationTimestamp
  @Column(name = "updated_date")
  protected ZonedDateTime updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_from")
  private String updatedFrom;

  @JsonProperty(value = "message")
  private String message;
}
