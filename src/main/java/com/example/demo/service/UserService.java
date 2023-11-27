package com.example.demo.service;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  Collection<User> findAll();

  User findByUsername(String username);

  User create(UserDto user);

  User validateUserCredentials(String username, String password);

  UserDetails loadUserByUsername(String username);
}
