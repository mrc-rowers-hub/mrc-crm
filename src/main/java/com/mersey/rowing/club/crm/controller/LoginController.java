package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.controller.service.LoginService;
import com.mersey.rowing.club.crm.model.repository.User;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  @Autowired private LoginService loginService;

  @PostMapping
  public ResponseEntity<String> login(@RequestBody User loginRequest) {

    Map<Boolean, String> isRegistered = loginService.isNowLoggedIn(loginRequest);
    Boolean registered = isRegistered.keySet().iterator().next();
    String responseMessage = isRegistered.values().stream().findAny().get();

    if (registered) {
      return ResponseEntity.ok(responseMessage);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
    }
  }
}
