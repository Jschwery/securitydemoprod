package com.jschwery.securitydemo.services.Implementations;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.models.UserModel;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    PasswordEncoder passEncoder;
    UserRepository  userRepository;

    @Override
    public Optional<User> saveUser(UserModel userModel) {
        if(!Objects.equals(userModel.getPassword(), userModel.getMatchPassword())){
            throw new UserException("Passwords do not match");
        }
        User user = User.builder()
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .password(passEncoder.encode(userModel.getPassword()))
                .roleName(Set.of("USER"))
                .timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
        User returnedUser = userRepository.save(user);
        return Optional.of(returnedUser);
    }
}
