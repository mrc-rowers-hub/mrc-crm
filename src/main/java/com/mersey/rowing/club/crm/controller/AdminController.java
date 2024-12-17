package com.mersey.rowing.club.crm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdminController {
    @GetMapping("/request_new_users")
    public void requestNewUsers(@RequestParam(defaultValue = "1") int amount) {
        log.info("Amount: " + amount);
    }
}
