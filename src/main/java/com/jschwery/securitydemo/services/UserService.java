package com.jschwery.securitydemo.services;

import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(UserDTO user);
}
