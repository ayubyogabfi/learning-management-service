//package com.example.demo.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//import com.example.demo.dto.LoginRequest;
//import com.example.demo.dto.LoginResponse;
//import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//
//class AuthControllerTest {
//
//  @Mock
//  private UserService userService;
//
//  @InjectMocks
//  private AuthController authController;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  void login_InvalidCredentials_ReturnsUnauthorized() {
//    // Arrange
//    LoginRequest loginRequest = new LoginRequest();
//    loginRequest.setUsername("invalidUser");
//    loginRequest.setPassword("invalidPassword");
//
//    when(userService.validateUserCredentials("invalidUser", "invalidPassword"))
//      .thenThrow(new BadCredentialsException("Invalid username or password"));
//
//    // Act
//    ResponseEntity<LoginResponse> response = authController.login(loginRequest);
//
//    // Assert
//    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//    verify(userService, times(1)).validateUserCredentials("invalidUser", "invalidPassword");
//  }
//
//  @Test
//  void login_ExceptionThrown_ReturnsBadRequest() {
//    // Arrange
//    LoginRequest loginRequest = new LoginRequest();
//    loginRequest.setUsername("someUser");
//    loginRequest.setPassword("somePassword");
//
//    when(userService.validateUserCredentials("someUser", "somePassword"))
//      .thenThrow(new RuntimeException("Some exception"));
//
//    // Act
//    ResponseEntity<LoginResponse> response = authController.login(loginRequest);
//
//    // Assert
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    verify(userService, times(1)).validateUserCredentials("someUser", "somePassword");
//  }
//}
