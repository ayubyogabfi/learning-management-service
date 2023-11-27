package com.example.demo.controller;

import static com.example.demo.constants.APIConstants.REFRESH_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.example.demo.auth.UserDetailsImpl;
import com.example.demo.constants.APIConstants;
import com.example.demo.constants.AppConstants;
import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = APIConstants.AUTH_PATH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  @Operation(summary = "Refresh access token", description = "Refresh access token description")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "New access token successfully obtained"),
      @ApiResponse(
        responseCode = "404",
        description = "User not found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
    }
  )
  @PostMapping(path = REFRESH_TOKEN_PATH)
  public void refreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith(AppConstants.BEARER)) {
      try {
        final String refreshToken = authorizationHeader.substring(AppConstants.BEARER.length());
        final String username = JwtUtil.getSubject(refreshToken);
        final User user = userService.findByUsername(username);
        final String accessToken = JwtUtil.createToken(user);
        ResponseUtil.responseTokensWithUserInfo(response, accessToken, refreshToken, user);
      } catch (Exception exception) {
        log.error("Fail to refresh token {}", exception.getMessage());
        ResponseUtil.responseInvalidToken(exception, response);
      }
    } else {
      throw new BadRequestException("Refresh token is empty");
    }
  }

  @PostMapping(value = "/login")
  public ResponseEntity<LoginResponse> login(
          @RequestBody LoginRequest loginRequest
  ) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      String accessToken = JwtUtil.createToken((UserDetailsImpl) authentication.getPrincipal());

      LoginResponse response = new LoginResponse();
      response.setUsername(loginRequest.getUsername());
      response.setAccessToken(accessToken);

      return ResponseEntity.ok(response);

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
