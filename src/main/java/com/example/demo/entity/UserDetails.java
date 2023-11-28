package com.example.demo.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetails extends User {

  public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
}
