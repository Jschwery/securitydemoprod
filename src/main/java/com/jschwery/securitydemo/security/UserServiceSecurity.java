package com.jschwery.securitydemo.security;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class UserServiceSecurity implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User userFound = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("Could not find user with the username: %s", username)));

        UserService userService = (UserService) withUsername(userFound.getUsername())
                .password(userFound.getPassword())
                .authorities(userFound.getRoleName()).build();

        return (UserDetails) userService;
    }
}