package com.mersey.rowing.club.crm.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RegistrationControllerTests {

  @Autowired private MockMvc mockMvc;

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private RegistrationController registrationController;

  private static MockedStatic<UserAuthenticationUtils> mockedUtils;

  private static User user = new User();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
  }

  @BeforeAll
  static void init() {
    user.setUsername("newuser");
    user.setPassword("password");
    mockedUtils = mockStatic(UserAuthenticationUtils.class);
  }

  @Test
  void registerUser_validUser_registersUser() throws Exception {
    mockedUtils
        .when(() -> UserAuthenticationUtils.assertUserIsValidToRegister(user))
        .thenReturn(true);

    mockMvc
        .perform(
            post("/register")
                .contentType("application/json")
                .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("User 'newuser' registered successfully"));
  }

  @Test
  void registerUser_passwordMissing_returnsBadRequest() throws Exception {
    mockedUtils
        .when(() -> UserAuthenticationUtils.assertUserIsValidToRegister(user))
        .thenReturn(false);

    mockMvc
        .perform(
            post("/register")
                .contentType("application/json")
                .content("{\"username\":\"newuser\", \"password\":null}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Insufficient information provided for registration"));

    mockedUtils.close();
  }
}
