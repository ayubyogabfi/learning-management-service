package com.example.demo.auth;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtInfoUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userInfo = repository.findUserAccountByUsername(username);
    return userInfo
      .map(JwtInfoUserDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
  }
}
