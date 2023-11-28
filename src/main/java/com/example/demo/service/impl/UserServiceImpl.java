package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetails;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Collection<User> findAll() {
    log.info(AppConstants.FIND_ALL_LOG);
    return userRepository.findAll();
  }

  @Override
  public User findUserAccountByUsername(String username) {
    log.info(AppConstants.FIND_LOG, username);
    return userRepository
      .findUserAccountByUsername(username)
      .orElseThrow(() -> new NotFoundException(String.format(AppConstants.NOT_FOUND_BY_USERNAME, username)));
  }

  @Override
  public User create(UserDto user) {
    log.info(AppConstants.CREATE_LOG, user.getName());
    checkUsername(user.getUsername());
    checkEmail(user.getEmail());

    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    newUser.setEmail(user.getEmail());
    newUser.setName(user.getName());
    newUser.setRoles("ADMIN");

    return userRepository.save(newUser);
  }

  @Override
  public LoginResponse validateUserCredentials(String username, String password) {
    LoginResponse user = userRepository
      .findPasswordByUsername(username)
      .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

    var decryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    if (!bCryptPasswordEncoder.matches(password, decryptedPassword)) {
      throw new BadCredentialsException("Invalid username or password");
    }

    return user;
  }

  private void checkUsername(String username) {
    Optional<User> user = userRepository.findUserAccountByUsername(username);
    if (user.isPresent()) {
      throw new ConflictException("The username already exists.");
    }
  }

  private void checkEmail(String email) {
    Optional<User> user = userRepository.findUserAccountByEmail(email);
    if (user.isPresent()) {
      throw new ConflictException("The email already exists.");
    }
  }

  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
    User user = findUserAccountByUsername(username);
    return new UserDetails(user.getUsername(), user.getPassword(), null);
  }
}