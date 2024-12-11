package com.mersey.rowing.club.crm.model.repository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;
}
