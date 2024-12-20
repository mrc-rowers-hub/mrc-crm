package com.mersey.rowing.club.crm.controller.service;

import com.mersey.rowing.club.crm.model.repository.UserCreationCode;
import com.mersey.rowing.club.crm.model.repository.UserCreationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreationCodes {

    @Autowired
    private UserCreationCodeRepository repository;

    public List<String> createNewUUID(int amount) {
        List<String> creationCodes = new ArrayList<>();

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
