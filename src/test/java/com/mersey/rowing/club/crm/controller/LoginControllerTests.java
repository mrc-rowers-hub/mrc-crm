package com.mersey.rowing.club.crm.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mersey.rowing.club.crm.controller.service.LoginService;
import com.mersey.rowing.club.crm.model.repository.User;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class LoginControllerTests {

  @Autowired private MockMvc mockMvc;

  @Mock private LoginService loginService;

  @InjectMocks private LoginController loginController;

  private static User user = new User();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
  }

  @BeforeAll
  static void init() {
    user.setUsername("newuser");
    user.setPassword("password");
  }

  @Test
  void login_invalidDetails_returnsUnauthorized() throws Exception {
    when(loginService.isNowLoggedIn(user))
        .thenReturn(Map.of(false, "Invalid username or password"));
    mockMvc
        .perform(
            post("/login")
                .contentType("application/json")
                .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Invalid username or password"));
  }

  @Test
  void login_validDetails_returnsOkAndLogsIn() throws Exception {
    when(loginService.isNowLoggedIn(user)).thenReturn(Map.of(true, "Login successful!"));
    mockMvc
        .perform(
            post("/login")
                .contentType("application/json")
                .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Login successful!"));
  }
}
