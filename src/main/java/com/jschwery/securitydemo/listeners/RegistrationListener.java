package com.jschwery.securitydemo.listeners;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserToken;
import com.jschwery.securitydemo.event.UserRegisterEmailEvent;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.service.Implementations.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class RegistrationListener implements ApplicationListener<UserRegisterEmailEvent> {
    @Autowired
    UserServiceImpl userService;
    @Override
    public void onApplicationEvent(UserRegisterEmailEvent event) {
        //create token for user with link
        User userFromEvent = event.getUser();
        String tokenToSendToUser = UUID.randomUUID().toString();

        UserToken token = new UserToken(tokenToSendToUser, userFromEvent);

        try {
            userService.addTokenToUser(token, userFromEvent, tokenToSendToUser);
        }catch (UserException userException){
            //logger log exception occurred
            throw new UserException(HttpStatus.BAD_REQUEST, "Incorrect input parameters");
        }




    }
}
