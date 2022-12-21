package com.jschwery.securitydemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Users")
public class User {

    @SequenceGenerator(name = "user_sequence_gen",
    allocationSize = 1, sequenceName = "user_sequence_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
    @Id
    @Column(name = "user_id")
    long userID;
    String email;
    String username;
    Timestamp timeCreated;
    String password;
    String firstName;
    String lastName;
    Set<String> roleName;

}
