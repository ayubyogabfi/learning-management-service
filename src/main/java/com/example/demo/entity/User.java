package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

@Entity(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "name")
  private String name;

  @Column(unique = true, name = "email")
  private String email;

  @Column(unique = true, name = "username")
  private String username;

  @Column(nullable = false, name = "password")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(name = "roles")
  private String roles;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
