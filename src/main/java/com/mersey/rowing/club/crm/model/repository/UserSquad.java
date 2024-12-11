package com.mersey.rowing.club.crm.model.repository;

import com.mersey.rowing.club.crm.model.enums.RowerLevel;
import com.mersey.rowing.club.crm.model.enums.Squad;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@ToString
@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSquad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "squad", columnDefinition = "ENUM('WOMENS', 'DEVELOPMENT', 'MENS')")
  @Enumerated(EnumType.STRING)
  private Squad squad;

  @Column(
      name = "level",
      columnDefinition = "ENUM('DEVELOPMENT', 'NOVICE', 'INTERMEDIATE', 'SENIOR')")
  @Enumerated(EnumType.STRING)
  private RowerLevel level;
}
