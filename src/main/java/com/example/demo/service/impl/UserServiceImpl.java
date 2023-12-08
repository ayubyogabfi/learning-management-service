package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.security.SecureRandom;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User create(UserDto user) {
    log.info(AppConstants.CREATE_LOG, user.getName());
    checkUsername(user.getUsername());

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);

    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12, random)));
    newUser.setEmail(user.getEmail());
    newUser.setName(user.getName());
    newUser.setRoles("ADMIN");

    return userRepository.save(newUser);
  }

  @Override
  public boolean checkPassword(LoginRequest loginRequest) {
    Optional<User> user = userRepository.findUserAccountByUsernameAndDeletedDateIsNull(loginRequest.getUsername());
    return user.filter(value -> BCrypt.checkpw(loginRequest.getPassword(), value.getPassword())).isPresent();
  }

  private void checkUsername(String username) {
    Optional<User> user = userRepository.findUserAccountByUsername(username);
    if (user.isPresent()) {
      throw new ConflictException("The username already exists.");
    }
  }
}
