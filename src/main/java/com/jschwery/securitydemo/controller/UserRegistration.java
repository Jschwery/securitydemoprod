package com.jschwery.securitydemo.controller;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRegistration {

    UserService userService;

    UserRegistration(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ResponseEntity<User> userResponseEntity() {
        User inputUser = User.builder().
                email("testemail@gmail.com").
                password("password").
                firstName("john").
                lastName("doe").
                timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();

        return new ResponseEntity<>(inputUser, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> userRegistration(@RequestBody UserModel userModel){

        User user = userService.saveUser(userModel);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
