package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.model.repository.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthenticationUtils {
    private static String message = "REGISTRATION DENIED: ";

    public static boolean assertUserIsValidToRegister(User user) {
        if (user == null) {
            log.warn(message + "User object cannot be null");
            return false;
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            log.warn(message + "Username is required");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            log.warn(message + "Password is required");
            return false;
        }
        return true;
    }
}
