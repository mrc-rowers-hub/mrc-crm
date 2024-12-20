package com.mersey.rowing.club.crm.controller;

import com.mersey.rowing.club.crm.controller.service.CreationCodesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, List<String>> requestNewUsers(@RequestParam(defaultValue = "1") int amount) {
        List<String> codes = creationCodesService.createNewUUID(amount);
        return Map.of("creation_codes", codes);
    }
}
