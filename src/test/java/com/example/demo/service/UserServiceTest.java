package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.UserRepository;

import java.time.Instant;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public class UserBuilder {
        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder withUsername(String username) {
            user.setUsername(username);
            return this;
        }

        public UserBuilder withEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder withPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public User build() {
            return user;
        }
    }
    
    @Test
    void testLoadUserByUsername_incorrectPassword() {
        User user = new UserBuilder()
                .withUsername("ayubyoga")
                .withPassword("123123")
                // Set other required parameters
                .build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userService.loadUserByUsername("janedoe");
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("ayubyoga", actualLoadUserByUsernameResult.getUsername());
        assertNotEquals("231231", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    void testLoadUserByUsername() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(NotFoundException.class, () -> userService.loadUserByUsername("janedoe"));
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
    void testFindByUsername() {
        User user = new UserBuilder()
                .withUsername("janedoe")
                .withPassword("iloveyou")
                // Set other required parameters
                .build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertSame(user, userService.findByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    void testCreate() {
        User user = new UserBuilder()
                .withUsername("ayubyoga")
                .withPassword("231231")
                .build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(ConflictException.class,
                () -> userService.create(new UserDto("Name", "jane.doe@example.org", "janedoe", "iloveyou")));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(Mockito.<Integer>any());
        userService.delete(1);
        verify(userRepository).deleteById(Mockito.<Integer>any());
    }

}

