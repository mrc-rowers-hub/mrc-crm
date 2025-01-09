package com.mersey.rowing.club.crm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mersey.rowing.club.crm.controller.service.CreationCodesService;

import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class AdminControllerTests {
    @Autowired private MockMvc mockMvc;

    @Mock private CreationCodesService creationCodesService;

    @InjectMocks private AdminController adminController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    // number of codes requested == number of codes returned
    @Test
    void shouldReturnCorrectNumberOfCodes() throws Exception {
        int amount = 5;
        List<String> mockCodes = List.of("uuid1", "uuid2", "uuid3", "uuid4", "uuid5");
        when(creationCodesService.createNewUUID(amount)).thenReturn(mockCodes);

        mockMvc.perform(get("/request_new_users")
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creation_codes").isArray())
                .andExpect(jsonPath("$.creation_codes.length()").value(amount))
                .andExpect(jsonPath("$.creation_codes[0]").value("uuid1"));
    }

    // IllegalArgumentException when amount = 0 and amount = 31
    @Test
    void shouldReturnBadRequestForInvalidAmountBelow() throws Exception {
        int invalidAmount = 0;

        Mockito.when(creationCodesService.createNewUUID(invalidAmount))
                .thenThrow(new IllegalArgumentException("Amount must be between 1 and 30"));

        mockMvc.perform(get("/request_new_users")
                .param("amount", String.valueOf(invalidAmount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Amount must be between 1 and 30"));
    }

    @Test
    void shouldReturnBadRequestForInvalidAmountAbove() throws Exception {
        int invalidAmount = 31;

        Mockito.when(creationCodesService.createNewUUID(invalidAmount))
                .thenThrow(new IllegalArgumentException("Amount must be between 1 and 30"));

        mockMvc.perform(get("/request_new_users")
                .param("amount", String.valueOf(invalidAmount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Amount must be between 1 and 30"));
    }

    // each uuid returned is unique
    @Test
    void shouldReturnUniqueUUIDs() throws Exception {
        int amount = 5;
        List<String> mockCodes = List.of("uuid1", "uuid2", "uuid3", "uuid4", "uuid5");
        when(creationCodesService.createNewUUID(amount)).thenReturn(mockCodes);

        mockMvc.perform(get("/request_new_users")
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creation_codes").isArray())
                .andExpect(jsonPath("$.creation_codes.length()").value(amount))
                .andDo(result -> {
                    List<String> codes = List.of("uuid1", "uuid2", "uuid3", "uuid4", "uuid5");
                    assertEquals(codes.size(), codes.stream().distinct().count());
                });
    }

    // correct response when amount = 1 and amount = 30
    @Test
    void shouldAcceptValidAmountOfOne() throws Exception {
        int amount = 1;
        List<String> mockCodes = List.of("uuid1");
        when(creationCodesService.createNewUUID(amount)).thenReturn(mockCodes);

        mockMvc.perform(get("/request_new_users")
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creation_codes").isArray())
                .andExpect(jsonPath("$.creation_codes.length()").value(amount));

    }

    @Test
    void shouldAcceptValidAmountOfThirty() throws Exception {
        int amount = 30;
        List<String> mockCodes = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            mockCodes.add("uuid" + i);
        }
        when(creationCodesService.createNewUUID(amount)).thenReturn(mockCodes);

        mockMvc.perform(get("/request_new_users")
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creation_codes").isArray())
                .andExpect(jsonPath("$.creation_codes.length()").value(amount));
    }
}
