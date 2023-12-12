package com.example.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.demo.auth.JwtService;
import com.example.demo.constants.APIConstants;
import com.example.demo.constants.AppConstants;
import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

  @Autowired
  private JwtService jwtService;

  @Operation(summary = "Create a user", description = "Create a user")
  @ApiResponse(responseCode = "201", description = "User successfully created")
  @PostMapping(consumes = APPLICATION_JSON_VALUE, value = APIConstants.REGISTER_ACCOUNT_PATH)
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

  @PostMapping("/v1/login")
  @Operation(summary = "User Login")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Login Successful"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials"),
      @ApiResponse(
        responseCode = "400",
        description = "Bad Request",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
    }
  )
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      if (!userService.checkPassword(loginRequest)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String accessToken = jwtService.generateToken(loginRequest.getUsername());

      User responses = new User();
      responses.setUsername(loginRequest.getUsername());
      responses.setAccessToken(accessToken);

      LoginResponse response = new LoginResponse();
      response.setUsername(responses.getUsername());
      response.setAccessToken(responses.getAccessToken());

      return ResponseEntity.ok(response);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
