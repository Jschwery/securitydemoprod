package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exception.UserException;
import com.jschwery.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
public class AdminSecurityConfig implements AuthenticationProvider {
    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(username);
        if(Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        }else {
            throw new UserException("Could not verify user from database");
        }
     }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
