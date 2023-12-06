package com.example.demo.repository;

import com.example.demo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findUserAccountByUsernameAndDeletedDateIsNull(String username);

  Optional<User> findUserAccountByUsername(String username);
}
