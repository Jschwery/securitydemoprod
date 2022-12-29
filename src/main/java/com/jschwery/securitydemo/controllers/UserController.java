package com.jschwery.securitydemo.controllers;

import com.jschwery.securitydemo.entities.UserRegistrationForm;
import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.Implementations.UserServiceImpl;
import com.jschwery.securitydemo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@EnableMethodSecurity
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    UserRepository repository;
    private final ProviderManager authenticationManager;

    public UserController(ProviderManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserDTO userModel = new UserDTO();
        model.addAttribute("userLogin", new UserDTO());
        return "/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLogin") UserDTO userDto, BindingResult bindingResult) {

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            return "redirect:/home/" + userDto.getUsername();
        }
        return "/login";
    }

        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/register")
        public String showRegistrationForm(Model model) {
            UserRegistrationForm registrationForm = new UserRegistrationForm();
            model.addAttribute("registrationForm", registrationForm);
            System.out.println("Registration form: " + registrationForm);
            return "registration";
        }

        @GetMapping("/home/{username}")
        public String userHome(@PathVariable String username, Model model){
            User user = repository.findByUsername(username).orElseThrow(() -> {
                return new UserException(String.format("Could not find user %s", username));
            });
            model.addAttribute("user", user);

            return "/home";
        }

        @PostMapping("/register")
        public String handleRegistration(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm,
                                         BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "registration";
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(userRegistrationForm.getUsername());
            userDTO.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
            userDTO.setFirstName(userRegistrationForm.getFirstName());
            userDTO.setLastName(userRegistrationForm.getLastName());
            userDTO.setRoleName(Set.of("ROLE_USER"));
            User users = new User();
            users.setUsername(userDTO.getEmail());
            users.setPassword(userDTO.getPassword());
            users.setFirstName(userDTO.getFirstName());
            users.setLastName(userDTO.getLastName());
            users.setRoleName(userDTO.getRoleName());
            userService.saveUser(users);
            return "redirect:/login";
        }
    }






