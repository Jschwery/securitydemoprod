package com.jschwery.securitydemo.service;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserToken;
import com.jschwery.securitydemo.model.UserModel;
import org.apache.el.parser.Token;

import java.sql.Timestamp;

public interface UserService {
    User saveUser(UserModel userModel);
    UserToken addTokenToUser(UserToken ut, User user, String token);
}
