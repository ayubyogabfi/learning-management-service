package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.dto.LoginRequest;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link AuthController#login(LoginRequest)}
     */
    @Test
    void testLogin() throws Exception {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("iloveyou");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AuthController#login(LoginRequest)}
     */
    @Test
    void testLogin2() throws Exception {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new BadCredentialsException("?"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("iloveyou");
        loginRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401));
    }

    /**
     * Method under test: {@link AuthController#refreshToken(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testRefreshToken() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/authenticate/refresh-token");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AuthController#refreshToken(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testRefreshToken2() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AuthController#refreshToken(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testRefreshToken3() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/authenticate/refresh-token");
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

