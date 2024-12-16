package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.controller.service.LoginService;
import com.mersey.rowing.club.crm.model.repository.User;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

  @Autowired private LoginService loginService;

  @PostMapping
  public ResponseEntity<String> register(@RequestBody User user) {
    Map<Boolean, String> isRegistered = loginService.isNowRegistered(user);
    Boolean registered = isRegistered.keySet().iterator().next();
    String responseMessage = isRegistered.values().stream().findAny().get();

    if (registered) {
      return ResponseEntity.ok(responseMessage);
    } else {
      return ResponseEntity.badRequest().body(responseMessage);
    }
  }
}
