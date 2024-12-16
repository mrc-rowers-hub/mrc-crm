package com.mersey.rowing.club.crm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mersey.rowing.club.crm.controller.service.LoginService;
import com.mersey.rowing.club.crm.controller.utils.UserAuthenticationUtils;
import com.mersey.rowing.club.crm.model.repository.User;
import com.mersey.rowing.club.crm.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RegistrationControllerTests {
    // todo have a parent of these too

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private RegistrationController registrationController;

    private static User user = new User();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @BeforeAll
    static void init() {
        user.setUsername("newuser");
            }

    @Test
    void registerUser_validUser_registersUser() throws Exception {
        user.setPassword("password");
        when(loginService.isNowRegistered(user)).thenReturn(Map.of(true, "User '" + user.getUsername() + "' registered successfully"));

        mockMvc
                .perform(
                        post("/register")
                                .contentType("application/json")
                                .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User 'newuser' registered successfully"));
    }

    @Test
    void registerUser_invalidRequest_returnsBadRequest() throws Exception {
        user.setPassword(null);
        when(loginService.isNowRegistered(user)).thenReturn(Map.of(false, "Insufficient information provided for registration"));

        mockMvc
                .perform(
                        post("/register")
                                .contentType("application/json")
                                .content("{\"username\":\"newuser\", \"password\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient information provided for registration"));
    }

}
