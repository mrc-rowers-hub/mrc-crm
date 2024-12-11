package com.mersey.rowing.club.crm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.mersey.rowing.club.crm.model.repository.UserRepository;

import com.mersey.rowing.club.crm.model.repository.UserRepository;

import com.mersey.rowing.club.crm.model.repository.User;


@SpringBootTest
@ExtendWith(MockitoExtension.class)  // Enable Mockito for JUnit 5
@AutoConfigureMockMvc

public class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationController registrationController;

    private MockedStatic<UserAuthenticationUtils> mockedUtils;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void registerUser_validUser_registersUser() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password"); // Missing password

        // Mocking static method assertUserIsValidToRegister to return true
        mockedUtils = mockStatic(UserAuthenticationUtils.class);
        mockedUtils.when(() -> UserAuthenticationUtils.assertUserIsValidToRegister(user)).thenReturn(true);
// need to mock the db here
        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User 'newuser' registered successfully"));
    }


    @Test
    void registerUser_PasswordMissing_ReturnsBadRequest() throws Exception {
        // Given
        User user = new User();
        user.setUsername("newuser");
        user.setPassword(null); // Missing password

        // Mocking static method assertUserIsValidToRegister to return false
        mockedUtils = mockStatic(UserAuthenticationUtils.class);
        mockedUtils.when(() -> UserAuthenticationUtils.assertUserIsValidToRegister(user)).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newuser\", \"password\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient information provided for registration"));

        // Verifications
        verify(userRepository, times(0)).save(any());
        verify(passwordEncoder, times(0)).encode(any());

        // Close static mock to avoid conflicts with other tests
        mockedUtils.close();
    }
}