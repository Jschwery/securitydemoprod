package com.jschwery.securitydemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationForm {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
