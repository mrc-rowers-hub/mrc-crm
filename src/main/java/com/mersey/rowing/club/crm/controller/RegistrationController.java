package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.model.repository.User;
import com.mersey.rowing.club.crm.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping
  public ResponseEntity<String> register(@RequestBody User user) {
    if (!UserAuthenticationUtils.assertUserIsValidToRegister(user)) {
      return ResponseEntity.badRequest().body("Insufficient information provided for registration");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    log.info("New user {} registered", user.getUsername());
    return ResponseEntity.ok("User '" + user.getUsername() + "' registered successfully");
  }
}
