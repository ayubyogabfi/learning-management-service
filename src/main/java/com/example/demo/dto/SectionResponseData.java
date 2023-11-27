package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponseData {

  @JsonProperty(value = "section_title")
  public String sectionTitle;

  @JsonProperty(value = "body")
  public String body;

}
