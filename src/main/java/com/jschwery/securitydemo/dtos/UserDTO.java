package com.jschwery.securitydemo.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    String email;
    Timestamp timeCreated;
    String username;
    String password;
    String firstName;
    String lastName;
}
