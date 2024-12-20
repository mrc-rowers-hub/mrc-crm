package com.mersey.rowing.club.crm.controller.service;

import com.mersey.rowing.club.crm.model.repository.UserCreationCode;
import com.mersey.rowing.club.crm.model.repository.UserCreationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreationCodesService {

    @Autowired
    private UserCreationCodeRepository repository;

    public List<String> createNewUUID(int amount) {
        List<String> creationCodes = new ArrayList<>();

        if (amount <= 0 || amount > 30) {
            throw new IllegalArgumentException("Amount must between 1 and 30");
        }

        for (int i = 0; i < amount; i++) {
            String uuid = UUID.randomUUID().toString();
            creationCodes.add(uuid);

            UserCreationCode entity = new UserCreationCode();
            entity.setCreationCode(uuid);
            repository.save(entity);
        }

        return creationCodes;
        
    }
}
