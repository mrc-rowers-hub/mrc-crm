package com.mersey.rowing.club.crm.model.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name  = "user_creation_codes")
@Getter
@Setter
public class UserCreationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creation_code", unique = true, nullable = false)
    private String creationCode;
}
