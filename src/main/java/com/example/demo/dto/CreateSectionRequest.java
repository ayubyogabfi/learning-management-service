package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSectionRequest {

  @NotBlank(message = "Section title must not be empty")
  @Size(min = 3, message = "Section title must be at least 3 characters long")
  @JsonProperty("section_title")
  private String sectionTitle;
}
