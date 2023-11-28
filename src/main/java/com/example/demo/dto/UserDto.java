package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @JsonProperty(required = true, value = "name")
  @NotBlank
  private String name;

  @JsonProperty(required = true, value = "email")
  @NotBlank
  @Email
  private String email;

  @JsonProperty(required = true, value = "username")
  @NotBlank
  private String username;

  @JsonProperty(required = true, value = "password")
  @NotBlank
  private String password;
}
