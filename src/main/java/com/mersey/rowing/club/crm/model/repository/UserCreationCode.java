package com.mersey.rowing.club.crm.model.repository;

import jakarta.persistence.*;

@Entity
@Table(name  = "user_creation_codes")
public class UserCreationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creation_code", unique = true, nullable = false)
    private String creationCode;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreationCode() {
        return creationCode;
    }

    public void setCreationCode(String creationCode) {
        this.creationCode = creationCode;
    }
}
