package com.example.demo.controller;

import com.example.demo.constants.APIConstants;
import com.example.demo.dto.LoginRequest;
import com.example.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = APIConstants.LOGIN_PATH, produces = APPLICATION_JSON_VALUE)
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest payload) {
        try {
            loginService.authenticateUser(payload.getUsername(), payload.getPassword());

            return ResponseEntity.ok("Login successful!");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed. Invalid username or password.");
        }
    }
}
