package com.jschwery.securitydemo.controller;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRegistration {
/*need to nail down roles
creating everyone is created as user
everyone should be able to access the registration and login form

After learning about the roles and validating which roles can access endpoints

what even authenticates with the database?



then create the thymeleaf login template and thymeleaf error template



 */


    Logger logger = LoggerFactory.getLogger(UserRegistration.class);
    UserService userService;
    @Autowired
    ApplicationEventPublisher userRegisterPublisher;

    UserRegistration(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping("/login.html")
    public String login() {
        return "/login.html";
    }


    @GetMapping("/registration")
    public String userResponseEntity() {
        return "registration";
    }
    @PostMapping("/registration")
    public ResponseEntity<User> userRegistration(@RequestBody UserModel userModel, HttpServletRequest request){
        User user = userService.saveUser(userModel).orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "Unable to register user with given inputs"));


        return new ResponseEntity<>(user, HttpStatus.OK);




    }
}
