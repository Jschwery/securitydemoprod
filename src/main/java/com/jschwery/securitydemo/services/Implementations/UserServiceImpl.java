package com.jschwery.securitydemo.services.Implementations;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    PasswordEncoder passEncoder;
    UserRepository  userRepository;

    @Autowired
    UserServiceImpl(PasswordEncoder encoder, UserRepository repository){
        this.passEncoder = encoder;
        this.userRepository = repository;
    }
    @Override
    public User saveUser(User userDTO) {
        User user = User.builder()
                .email(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(passEncoder.encode(userDTO.getPassword()))
                .username(userDTO.getUsername())
                .roleName(Set.of("ROLE_USER"))
                .timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
            User returnedUser = userRepository.save(user);
            return Optional.of(returnedUser).orElseThrow(() -> new UserException("User entered already exists"));

    }



}
