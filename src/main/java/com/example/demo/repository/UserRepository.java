package com.example.demo.repository;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  // query to check user credential by username and password
  @Query("SELECT u FROM users u WHERE u.username = ?1 AND u.password = ?2")
  Optional<User> validateUsernameAndPassword(String username, String password);
}
