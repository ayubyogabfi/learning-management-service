package com.example.demo.controller;

import com.example.demo.constants.FlowConstants;
import com.example.demo.service.UserService;
import com.example.demo.data.dto.ErrorResponse;
import com.example.demo.data.dto.UserDto;
import com.example.demo.data.model.Role;
import com.example.demo.data.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = FlowConstants.USERS_PATH, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private static final String USER_CREATED_LOG = "User was created:{}";
    private static final String USER_UPDATED_LOG = "User was updated:{}";

    private final UserService userService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Get all users", description = "Get all users description")
    @ApiResponse(responseCode = "200", description = "Users successfully obtained")
    @GetMapping
    public ResponseEntity<Collection<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Operation(summary = "Create a user", description = "Create a user description")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody UserDto user) {
        final User userCreated = userService.create(user);
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(FlowConstants.USERS_PATH + "/" + userCreated.getUserId()).toUriString());
        log.info(USER_CREATED_LOG, userCreated);
        return ResponseEntity.created(uri).body(userCreated);
    }
}
