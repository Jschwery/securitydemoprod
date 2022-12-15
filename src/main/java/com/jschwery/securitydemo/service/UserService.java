package com.jschwery.securitydemo.service;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserToken;
import com.jschwery.securitydemo.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.el.parser.Token;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(UserModel userModel);
    UserToken addTokenToUser(UserToken ut, User user, String token);
}
