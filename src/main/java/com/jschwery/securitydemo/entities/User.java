package com.jschwery.securitydemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @SequenceGenerator(name = "user_sequence_gen",
    allocationSize = 1, sequenceName = "user_sequence_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
    @Id
    long userID;
    String email;
    Timestamp timeCreated;
    String username;
    String password;
    String firstName;
    String lastName;
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    UserToken userToken;

}
