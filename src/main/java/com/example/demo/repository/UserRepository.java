package com.example.demo.repository;

import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findUserAccountByUsername(String username);
  Optional<User> findUserAccountByEmail(String email);
  Optional<LoginResponse> findUserByUsernameAndPassword(String username, String password);
}
