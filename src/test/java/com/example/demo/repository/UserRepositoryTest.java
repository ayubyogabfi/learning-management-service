package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindByUsername() {
        String username = "test123";
        User mockUser = new User();
        mockUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userRepository.findByUsername(username);

        assertEquals(mockUser, result.orElse(null));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testFindByEmail() {
        String email = "test123@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userRepository.findByEmail(email);

        assertEquals(mockUser, result.orElse(null));
        verify(userRepository, times(1)).findByEmail(email);
    }

}
