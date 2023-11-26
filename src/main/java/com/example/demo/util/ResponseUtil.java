package com.example.demo.util;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.demo.constants.AppConstants;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

public class ResponseUtil {

  public static final String MESSAGE_KEY = "message";

  public static void responseGenericForbidden(Exception exception, HttpServletResponse response) throws IOException {
    response.setHeader(AppConstants.HEADER_ERROR, exception.getMessage());
    response.setStatus(FORBIDDEN.value());
    Map<String, String> payload = Map.of(MESSAGE_KEY, exception.getMessage());
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), payload);
  }

  public static void responseInvalidToken(Exception exception, HttpServletResponse response) throws IOException {
    response.setHeader(AppConstants.HEADER_ERROR, exception.getMessage());
    response.setStatus(FORBIDDEN.value());
    Map<String, String> payload = Map.of(MESSAGE_KEY, "Invalid session. Please login again.");
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), payload);
  }

  public static void responseBadCredentials(HttpServletResponse response, AuthenticationException failed)
    throws IOException {
    response.setHeader(AppConstants.HEADER_ERROR, failed.getMessage());
    response.setStatus(FORBIDDEN.value());
    String message = "Username or password incorrect";
    Map<String, String> payload = Map.of(MESSAGE_KEY, message);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), payload);
  }

  public static void responseTokens(HttpServletResponse response, String accessToken, String refreshToken)
    throws IOException {
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), JwtUtil.mapTokens(accessToken, refreshToken));
  }

  public static void responseTokensWithUserInfo(
    HttpServletResponse response,
    String accessToken,
    String refreshToken,
    User user
  ) throws IOException {
    response.setContentType(APPLICATION_JSON_VALUE);
    Map<String, String> payload = new java.util.HashMap<>(JwtUtil.mapTokens(accessToken, refreshToken));
    payload.put("uid", user.getUserId().toString());
    payload.put("username", user.getUsername());
    payload.put("email", user.getEmail());
    payload.put("name", user.getName());
    new ObjectMapper().writeValue(response.getOutputStream(), payload);
  }
}
