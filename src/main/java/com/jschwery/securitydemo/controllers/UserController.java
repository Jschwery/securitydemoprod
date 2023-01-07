package com.jschwery.securitydemo.controllers;

import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserRegistrationForm;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Controller
@EnableMethodSecurity
@Slf4j
@CrossOrigin
public class UserController {

    private final ProviderManager authenticationManager;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController(ProviderManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) boolean registrationSuccess) {
        UserDTO userModel = new UserDTO();

        return "/login";
    }

    @PostMapping("/login")
    public void login(@RequestBody UserDTO userDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.sendRedirect("/home");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/login?error=true");
        }
    }


    @GetMapping("/home/{username}")
    public String userHome(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        User user = repository.findByUsername(username).orElseThrow(() -> {
            return new UserException(String.format("Could not find user %s", username));
        });

        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegistration(@RequestBody UserRegistrationForm userRegistrationForm,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userRegistrationForm.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
        userDTO.setFirstName(userRegistrationForm.getFirstName());
        userDTO.setLastName(userRegistrationForm.getLastName());
        userDTO.setRoleName(Set.of("ROLE_USER"));
        userDTO.setUsername(userRegistrationForm.getUsername());

        User users = new User();
        users.setUsername(userDTO.getUsername());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        users.setFirstName(userDTO.getFirstName());
        users.setLastName(userDTO.getLastName());
        users.setRoleName(userDTO.getRoleName());
        try {
            User user = userService.saveUser(users);
            if(user != null) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return new ResponseEntity<>()
            }
        } catch (Exception e) {
            if (repository.findByUsername(users.getUsername()).isPresent()
                    && repository.findByEmail(users.getEmail()).isPresent()) {
                logger.warn(String.format("User already exists with the username: %s and the email: %s",
                        users.getUsername(), users.getEmail()));
                throw new UserException(String.format("User already exists with the username: %s and the email: %s",
                        users.getUsername(), users.getEmail()));
            } else if (repository.findByUsername(users.getUsername()).isPresent()) {
                logger.warn(String.format("User already exists with the username: %s", users.getUsername()));
                throw new UserException(String.format("User already exists with the username: %s", users.getUsername()));
            } else if (repository.findByEmail(users.getEmail()).isPresent()) {
                logger.warn(String.format("User already exists with the email: %s", users.getEmail()));
                throw new UserException(String.format("User already exists with the email: %s", users.getEmail()));
            }
        }
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.sendRedirect("/login?registrationSuccess=true");
    }
}