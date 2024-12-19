package com.mersey.rowing.club.crm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreationCodeRepository extends JpaRepository<UserCreationCode, Integer> {

}
