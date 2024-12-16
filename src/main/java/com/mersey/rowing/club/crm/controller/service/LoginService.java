package com.mersey.rowing.club.crm.controller.service;

import com.mersey.rowing.club.crm.controller.utils.UserAuthenticationUtils;
import com.mersey.rowing.club.crm.model.repository.User;
import com.mersey.rowing.club.crm.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<Boolean, String> isNowRegistered(User user){

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Map.of(false, "User already exists");
        }
        if (!UserAuthenticationUtils.isValidUsernameAndPassword(user)) {
            return Map.of(false, "Insufficient information provided for registration");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Map.of(true, "User '" + user.getUsername() + "' registered successfully");
    }

    public Map<Boolean, String> isNowLoggedIn(User loginRequest){
        if (!UserAuthenticationUtils.isValidUsernameAndPassword(loginRequest)) {
            return Map.of(false, "Invalid username or password");
        }
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of(false, "Invalid username or password");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(),  userOptional.get().getPassword())) {

            return Map.of(false, "Invalid username or password");
        }
        return Map.of(true, "Login successful!");

    }
}
