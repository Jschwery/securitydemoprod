package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.exceptions.UserException;
import com.jschwery.securitydemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomSecurityConfig implements AuthenticationProvider {

    UserRepository userRepository;
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(username).orElseThrow(()->  new UsernameNotFoundException("User not found"));
        if(Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(username),
                    userDetailsService.loadUserByUsername(username).getPassword(), userDetailsService.loadUserByUsername(username).getAuthorities());
        }else {
            throw new UserException("Could not verify user from database");
        }
     }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
