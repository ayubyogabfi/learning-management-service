package com.example.demo.config;

import com.example.demo.constants.AppConstants;
import com.example.demo.data.dto.RoleDto;
import com.example.demo.data.dto.UserDto;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            if (roleService.findAll().isEmpty()) {
                roleService.create(new RoleDto(AppConstants.ROLE_USER));
                roleService.create(new RoleDto(AppConstants.ROLE_MANAGER));
                roleService.create(new RoleDto(AppConstants.ROLE_ADMIN));
                roleService.create(new RoleDto(AppConstants.ROLE_SUPER_ADMIN));
            }
            if (userService.findAll().isEmpty()) {
                userService.create(new UserDto("Ayub Yoga", "ayubyoga@gmail.com", "ayubyoga", "231231"));

                userService.addRoleByUsername("ayubyoga", AppConstants.ROLE_USER);
                userService.addRoleByUsername("ayubyoga", AppConstants.ROLE_MANAGER);
                userService.addRoleByUsername("ayubyoga", AppConstants.ROLE_SUPER_ADMIN);
            }
        };
    }
}
