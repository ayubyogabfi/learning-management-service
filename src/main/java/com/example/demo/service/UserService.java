package com.example.demo.service;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import java.util.Collection;

public interface UserService {
  Collection<User> findAll();

  User findUserAccountByUsername(String username);

  User create(UserDto user);

  User validateUserCredentials(String username, String password);
}
