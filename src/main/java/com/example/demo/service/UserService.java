package com.example.demo.service;

import com.example.demo.auth.UserDetailsImpl;
import com.example.demo.constants.AppConstants;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = findByUsername(username);
    return new UserDetailsImpl(user, null);
  }

  public Collection<User> findAll() {
    log.info(AppConstants.FIND_ALL_LOG);
    return userRepository.findAll();
  }

  public User findByUsername(String username) {
    log.info(AppConstants.FIND_LOG, username);
    return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new NotFoundException(String.format(AppConstants.NOT_FOUND_BY_USERNAME, username)));
  }

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

  private void checkUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      throw new ConflictException("The username already exists.");
    }
  }

  private void checkEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      throw new ConflictException("The email already exists.");
    }
  }

  public User validateUserCredentials(String username, String password) {
    User user = findByUsername(username);

    if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("Invalid username or password");
    }
    return user;
  }
}