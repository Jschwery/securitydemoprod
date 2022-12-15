package com.jschwery.securitydemo.service.Implementations;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.entities.UserToken;
import com.jschwery.securitydemo.event.UserRegisterEmailEvent;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.repository.UserTokenVerification;
import com.jschwery.securitydemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PasswordEncoder passEncoder;
    UserRepository  userRepository;
    UserTokenVerification userTokenVerification;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserTokenVerification userTokenRepo){
        this.userRepository = repository;
        this.userTokenVerification = userTokenRepo;
    }



    /*
    save user model from entered form to database, and map it to a user to store to database
    when user is saved the event takes place that takes in the user and the url

    new token will be registered to the user
     */

    public String httpRequestToString(HttpServletRequest request){
        return String.format("http://%s:%s%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }

    @Override
    public Optional<User> saveUser(UserModel userModel) {
        if(!Objects.equals(userModel.getPassword(), userModel.getMatchPassword())){
            throw new UserException("Passwords do not match");
        }
        User user = User.builder().
                email(userModel.getEmail()).
                firstName(userModel.getFirstName()).
                lastName(userModel.getLastName()).
                password(passEncoder.encode(userModel.getPassword())).
                timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
        User returnedUser = userRepository.save(user);
        return Optional.of(returnedUser);
    }

    @Override
    public UserToken addTokenToUser(UserToken ut, User user, String token) {
        ut.setToken(token);
        ut.setUser(user);
        ut.setTokenExpiration(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(5)));
        return userTokenVerification.save(ut);
    }
}
