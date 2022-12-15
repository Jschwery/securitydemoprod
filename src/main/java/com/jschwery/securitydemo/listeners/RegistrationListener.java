package com.jschwery.securitydemo.listeners;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserToken;
import com.jschwery.securitydemo.event.UserRegisterEmailEvent;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.service.Implementations.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class RegistrationListener implements ApplicationListener<UserRegisterEmailEvent> {

    Logger logger = LoggerFactory.getLogger(RegistrationListener.class);

    @Autowired
    UserServiceImpl userService;
    @Override
    public void onApplicationEvent(UserRegisterEmailEvent event) {
        //create token for user with link
        User userFromEvent = event.getUser();
        String tokenToSendToUser = UUID.randomUUID().toString();
        UserToken token = new UserToken(tokenToSendToUser, userFromEvent);
        String url = String.format("%saccountVerification?token=%s", event.getUrl(),  token);

        try {
            userService.addTokenToUser(token, userFromEvent, tokenToSendToUser);
            logger.info("click link to verify account {}", url);
        }catch (UserException userException){
            logger.info("Token could not be applied to user");
            throw new UserException(HttpStatus.BAD_REQUEST, "Incorrect input parameters");
        }





    }
}
