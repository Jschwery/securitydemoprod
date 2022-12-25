package com.jschwery.securitydemo.services.Implementations;

import com.jschwery.securitydemo.dtos.UserDTO;
import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.UserService;
import lombok.RequiredArgsConstructor;
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
    public Optional<User> saveUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(passEncoder.encode(userDTO.getPassword()))
                .roleName(Set.of("USER"))
                .timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).build();
        User returnedUser = userRepository.save(user);
        return Optional.of(returnedUser);
    }
}
