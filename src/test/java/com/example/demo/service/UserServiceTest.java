package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testLoadUserByUsername() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(NotFoundException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    void testLoadUserByUsername2() {
        when(userRepository.findByUsername(Mockito.<String>any())).thenThrow(new ConflictException("An error occurred"));
        assertThrows(ConflictException.class, () -> userService.loadUserByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    void testFindAll() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        Collection<User> actualFindAllResult = userService.findAll();
        assertSame(userList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(userRepository).findAll();
    }
    @Test
    void testCreate() {
        User user = new User();
        user.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setCreatedBy("Jan 1, 2023 8:00am GMT+0100");
        user.setCreatedFrom("test123@example.org");
        user.setEmail("test123@example.org");
        user.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setName("test123");
        user.setPassword("231231");
        user.setRoles("ADMIN");
        user.setUpdatedBy("2023-03-01");
        user.setUpdatedDate("2023-03-01");
        user.setUpdatedFrom("2023-03-01");
        user.setUserId(1);
        user.setUsername("test123");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(ConflictException.class,
                () -> userService.create(new UserDto("test123", "test123@example.org",
                        "test123", "231231")));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserService#validateUserCredentials(String, String)}
     */
    @Test
    void testValidateUserCredentials() {
        User user = new User();
        user.setCreatedAt(LocalDate.of(2023, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setCreatedBy("Jan 1, 2023 8:00am GMT+0100");
        user.setCreatedFrom("test123@example.org");
        user.setEmail("test123@example.org");
        user.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setName("test123");
        user.setPassword("231231");
        user.setRoles("ADMIN");
        user.setUpdatedBy("2023-03-01");
        user.setUpdatedDate("2023-03-01");
        user.setUpdatedFrom("2023-03-01");
        user.setUserId(1);
        user.setUsername("test123");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(BadCredentialsException.class, () -> userService.validateUserCredentials(
                "test123", "231231"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }
}

