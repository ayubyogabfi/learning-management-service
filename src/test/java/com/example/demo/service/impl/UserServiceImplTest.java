package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser_success() {
        User user = new User();
        user.setCreatedBy("username");
        user.setCreatedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        user.setCreatedFrom("testemail@gmail.com");
        user.setDeletedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        user.setEmail("jane.doe@example.org");
        user.setName("testname");
        user.setPassword("testpassword");
        user.setRoles("ADMIN");
        user.setUpdatedBy("2023-11-11");
        user.setUpdatedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        user.setUpdatedFrom("2023-11-11");
        user.setUserId(1);
        user.setUsername("testusername");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserAccountByUsername(Mockito.any())).thenReturn(ofResult);
        assertThrows(ConflictException.class,
                () -> userServiceImpl.create(new UserDto("testname", "testemail@gmail.com",
                        "testusername", "testpassword")));
        verify(userRepository).findUserAccountByUsername(Mockito.any());
    }

    @Test
    void testCheckPassword() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findUserAccountByUsernameAndDeletedDateIsNull(Mockito.<String>any())).thenReturn(emptyResult);
        assertFalse(userServiceImpl.checkPassword(new LoginRequest("testusername", "testpassword")));
        verify(userRepository).findUserAccountByUsernameAndDeletedDateIsNull(Mockito.<String>any());
    }
}

