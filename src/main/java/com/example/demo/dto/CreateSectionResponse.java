package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSectionResponse {

  @JsonProperty(value = "section_title")
  private String sectionTitle;

  @JsonProperty(value = "message")
  private String message;
}
