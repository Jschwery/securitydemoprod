package com.jschwery.securitydemo.controllers;

import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.models.UserModel;
import com.jschwery.securitydemo.security.DetailService;
import com.jschwery.securitydemo.security.UserPrincipal;
import com.jschwery.securitydemo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@EnableMethodSecurity
@RequiredArgsConstructor
public class UserController {



    Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    private final AuthenticationManager authenticationManager;


    @RequestMapping("/login.html")
    public String login(@RequestBody UserDTO dto) {

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        return "/login.html";
    }


    @GetMapping("/registration")
    public String userResponseEntity(Model model, UserDTO userDTO) {
        model.addAttribute("userDTO", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("user") UserDTO user){
        User userToSubmit = new User();
        userToSubmit.setEmail(user.getEmail());
        userToSubmit.setFirstName(user.getFirstName());
        userToSubmit.setLastName(user.getLastName());
        userToSubmit.setRoleName(Collections.singleton("USER"));
        userToSubmit.setTimeCreated(user.getTimeCreated());

        try {
            User userReturned = userService.saveUser(userToSubmit).orElseThrow(
                    () -> new UserException(HttpStatus.BAD_REQUEST, "Unable to register user with given inputs"));
        }catch (UserException e){
            logger.error("Could not save the given user to the database");
            return "redirect:/registration?error=true";
        }
        logger.info(String.format("User: %s has been registered successfully\nTime: %s", userToSubmit.getUsername(),
                userToSubmit.getTimeCreated()));
        return "registration?user-created=true";
    }
    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public String getUserHome(Model model){
        return "home";
    }
}
