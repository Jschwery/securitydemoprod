package com.jschwery.securitydemo.service;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.model.UserModel;

public interface UserService {
    User saveUser(UserModel userModel);
}
