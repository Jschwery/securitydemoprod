package com.jschwery.securitydemo.service.Implementations;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    UserRepository  userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.userRepository = repository;
    }

    @Override
    public User saveUser(UserModel userModel) {
        if(!Objects.equals(userModel.getPassword(), userModel.getMatchPassword())){
            throw new UserException("Passwords do not match");
        }
        User user = User.builder().
                email(userModel.getEmail()).
                firstName(userModel.getFirstName()).
                lastName(userModel.getLastName()).
                password(userModel.getPassword()).
                timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
        User returnedUser = userRepository.save(user);
        System.out.println(returnedUser.getEmail());
        System.out.println("userID" + returnedUser.getUserID());
        return returnedUser;
    }
}
