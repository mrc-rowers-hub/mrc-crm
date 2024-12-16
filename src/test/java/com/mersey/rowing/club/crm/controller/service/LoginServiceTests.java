package com.mersey.rowing.club.crm.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.mersey.rowing.club.crm.controller.utils.UserAuthenticationUtils;
import com.mersey.rowing.club.crm.model.repository.User;
import com.mersey.rowing.club.crm.model.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {

  @Mock private UserRepository userRepository;

  @InjectMocks private LoginService loginService;

  private static MockedStatic<UserAuthenticationUtils> mockedUtils;
  @Mock private PasswordEncoder passwordEncoder;
  private static User user = new User();

  @BeforeAll
  static void init() {
    user.setUsername("newuser");
    user.setPassword("password");
    mockedUtils = mockStatic(UserAuthenticationUtils.class);
  }

  @Test
  void isNowRegistered_validUser_registersUser() {
    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(true);
    assertEquals(
        Map.of(true, "User '" + user.getUsername() + "' registered successfully"),
        loginService.isNowRegistered(user));
  }

  @Test
  void isNowRegistered_passwordMissing_returnsFalse() {
    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(false);
    assertEquals(
        Map.of(false, "Insufficient information provided for registration"),
        loginService.isNowRegistered(user));
  }

  @Test
  void isNowRegistered_userAlreadyExists_returnsFalse() {

    when(userRepository.findByUsername("newuser")).thenReturn(Optional.of(user));

    assertEquals(Map.of(false, "User already exists"), loginService.isNowRegistered(user));
  }

  @Test
  void isNowLoggedIn_passwordInvalid_returnsFalse() {
    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(false);
    assertEquals(Map.of(false, "Invalid username or password"), loginService.isNowLoggedIn(user));
  }

  @Test
  void isNowLoggedIn_validRegisteredUser_returnsTrue() {
    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(true);
    when(userRepository.findByUsername("newuser")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

    assertEquals(Map.of(true, "Login successful!"), loginService.isNowLoggedIn(user));
  }

  @Test
  void isNowLoggedIn_userDoesntExist_returnsFalse() {
    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(true);
    when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
    assertEquals(Map.of(false, "Invalid username or password"), loginService.isNowLoggedIn(user));
  }

  @Test
  void isNowLoggedIn_passwordMismatch_returnsFalse() {

    mockedUtils
        .when(() -> UserAuthenticationUtils.isValidUsernameAndPassword(user))
        .thenReturn(true);
    when(userRepository.findByUsername("newuser")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
    assertEquals(Map.of(false, "Invalid username or password"), loginService.isNowLoggedIn(user));
  }
}
