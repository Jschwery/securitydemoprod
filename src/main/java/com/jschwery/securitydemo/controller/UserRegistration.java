package com.jschwery.securitydemo.controller;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.event.UserRegisterEmailEvent;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/user")
public class UserRegistration {


    Logger logger = LoggerFactory.getLogger(UserRegistration.class);
    UserService userService;
    @Autowired
    ApplicationEventPublisher userRegisterPublisher;

    UserRegistration(UserService userService) {
        this.userService = userService;
    }

    public String httpRequestToString(HttpServletRequest request){
        return String.format("http://%s:%s%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }

    @RequestMapping("/login.html")
    public String login() {
        return "/login.html";
    }


    @GetMapping("/registration")
    public ResponseEntity<User> userResponseEntity() {
        User inputUser = User.builder().
                email("testemail@gmail.com").
                password("password").
                firstName("john").
                lastName("doe").
                timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
        logger.info("User " + inputUser + "created at: " + inputUser.getTimeCreated());
        return new ResponseEntity<>(inputUser, HttpStatus.OK);
    }
    @PostMapping("/registration")
    public ResponseEntity<User> userRegistration(@RequestBody UserModel userModel, HttpServletRequest request){
        User user = userService.saveUser(userModel).orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "Unable to register user with given inputs"));
        userRegisterPublisher.publishEvent(new UserRegisterEmailEvent(user, httpRequestToString(request)));


        return new ResponseEntity<>(user, HttpStatus.OK);




    }
}
