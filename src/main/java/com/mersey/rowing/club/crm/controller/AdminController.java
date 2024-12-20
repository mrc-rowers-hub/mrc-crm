package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.controller.service.CreationCodesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private CreationCodesService creationCodesService;

    @GetMapping("/request_new_users")
    public ResponseEntity<?> requestNewUsers(@RequestParam(defaultValue = "1") int amount) {
        try {
            List<String> codes = creationCodesService.createNewUUID(amount);
            return ResponseEntity.ok(Map.of("creation_codes", codes));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
