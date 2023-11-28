package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  @JsonProperty(required = true, value = "username")
  @NotBlank
  private String username;

  @JsonProperty(required = true, value = "password")
  @NotBlank
  private String password;
}
