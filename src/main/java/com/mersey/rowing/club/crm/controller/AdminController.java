package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.controller.service.CreationCodesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private CreationCodesService creationCodesService;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException e) {
        return Map.of("error", e.getMessage());
    }

    @GetMapping("/request_new_users")
    public ResponseEntity<?> requestNewUsers(@RequestParam(defaultValue = "1") int amount) {
        List<String> codes = creationCodesService.createNewUUID(amount);
        return ResponseEntity.ok(Map.of("creation_codes", codes));
    }
}
