package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public User user(){
        return new User();
    }
}
