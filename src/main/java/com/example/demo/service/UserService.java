package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import java.util.Collection;

public interface UserService {
  User findUserAccountByUsername(String username);

  User create(UserDto user);

  boolean validateUserCredentials(LoginRequest loginRequest);
}
