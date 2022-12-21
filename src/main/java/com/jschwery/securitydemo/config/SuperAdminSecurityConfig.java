package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SuperAdminSecurityConfig {
    @Autowired
    UserRepository  userRepository;

}
