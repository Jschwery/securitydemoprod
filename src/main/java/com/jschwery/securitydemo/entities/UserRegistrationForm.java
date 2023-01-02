package com.jschwery.securitydemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class UserRegistrationForm {

    @Id
    @SequenceGenerator(name = "user_Reg_Form_Gen",
            allocationSize = 1, sequenceName = "user_Reg_Form_Gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_Reg_Form_Gen")
    Long registerFormID;

    @NotNull(message = "Username is required")
    @Size(min = 3, max = 16, message = "Username must be between 3 and 16 characters")
    private String username;
    @NotNull(message = "Please enter a password")
    private String password;
    private String matchPassword;
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Email is required")
    private String email;
}
