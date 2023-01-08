package com.jschwery.securitydemo.controllers;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableMethodSecurity
//@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository repository;


    @GetMapping("/home/{username}")
    public ResponseEntity<?> userHome(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        User user = repository.findByUsername(username).orElseThrow(() -> {
            return new UserException(String.format("Could not find user %s", username));
        });

        return ResponseEntity.ok(user);
    }

}
