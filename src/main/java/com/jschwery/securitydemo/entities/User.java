package com.jschwery.securitydemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"username"})
})
public class User {

    @SequenceGenerator(name = "user_sequence_gen",
    allocationSize = 1, sequenceName = "user_sequence_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
    @Id
    @Column(name = "user_id")
    long userID;

    @NotNull(message = "Email is required")
    String email;

    @NotNull(message = "Username is required")
    @Size(min = 3, max = 16, message = "Username must be between 3 and 16 characters")
    String username;

    Timestamp timeCreated;

    @NotNull(message = "Please enter a password")
    String password;

    @NotNull(message = "First name is required")
    String firstName;

    @NotNull(message = "Last name is required")
    String lastName;

    Set<String> roleName;
}
