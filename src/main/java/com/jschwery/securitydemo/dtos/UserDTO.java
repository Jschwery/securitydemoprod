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


    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
