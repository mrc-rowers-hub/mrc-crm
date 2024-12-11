package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.model.repository.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserAuthenticationUtilsTests {

    private static User user = new User();

    @BeforeAll
    static void init() {
        user.setUsername("newuser");
        user.setPassword("password");
    }

    @Test
    public void testValidUser() {
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(user);
        assertTrue(isValid);
    }

    @Test
    public void testNullUser() {
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(null);
        assertFalse(isValid);
    }

    @Test
    public void testEmptyUsername() {
        user.setUsername("");
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(user);
        assertFalse(isValid);
    }

    @Test
    public void testEmptyPassword() {
        user.setPassword("");
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(user);
        assertFalse(isValid);
    }

    @Test
    public void testWhitespaceUsername() {
        user.setUsername(" ");
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(user);
        assertFalse(isValid);
    }

    @Test
    public void testWhitespacePassword() {
        user.setPassword(" ");
        boolean isValid = UserAuthenticationUtils.assertUserIsValidToRegister(user);
        assertFalse(isValid);
    }
}
