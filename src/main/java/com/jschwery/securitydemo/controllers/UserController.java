package com.jschwery.securitydemo.controllers;

import com.jschwery.securitydemo.entities.UserRegistrationForm;
import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@EnableMethodSecurity
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
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
    public String login(@ModelAttribute("userLogin") UserDTO userDto) {

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            return "/home/" + userDto.getUsername();
        }
        return "/login?error";
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

        @PostMapping("/register")
        public String handleRegistration(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm,
                                         BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "registration";
            }

            User user = new User();
            user.setEmail(userRegistrationForm.getUsername());
            user.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
            user.setFirstName(userRegistrationForm.getFirstName());
            user.setLastName(userRegistrationForm.getLastName());

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userService.saveUser(userDTO);
            return "redirect:/login";
        }
    }






