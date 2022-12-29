package com.jschwery.securitydemo.services;

import com.jschwery.securitydemo.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(User user);
}
