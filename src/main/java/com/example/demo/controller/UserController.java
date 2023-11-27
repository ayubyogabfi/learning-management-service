package com.example.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.demo.constants.APIConstants;
import com.example.demo.constants.AppConstants;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @Operation(summary = "Create a user", description = "Create a user description")
  @ApiResponse(responseCode = "201", description = "User successfully created")
  @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/register-account")
  public ResponseEntity<User> create(@Valid @RequestBody UserDto user) {
    final User userCreated = userService.create(user);
    final URI uri = URI.create(
      ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(APIConstants.USERS_PATH + "/" + userCreated.getUserId())
        .toUriString()
    );
    log.info(AppConstants.USER_CREATED_LOG, userCreated);
    return ResponseEntity.created(uri).body(userCreated);
  }
}
