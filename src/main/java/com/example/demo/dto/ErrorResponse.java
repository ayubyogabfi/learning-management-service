package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  @JsonProperty(value = "timestamp")
  private Instant timestamp;

  @JsonProperty(value = "status")
  private int status;

  @JsonProperty(value = "reason")
  private String reason;

  @JsonProperty(value = "errors")
  private List<String> errors;

  @JsonProperty(value = "type")
  private String type;

  @JsonProperty(value = "path")
  private String path;

  @JsonProperty(value = "message")
  private String message;
}
