package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.security.DetailService;
import com.jschwery.securitydemo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {
    @Autowired
    DetailService userDetailService;
    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    @Autowired
    AuthenticationProvider authProvider(PasswordEncoder encoder){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    @Autowired
    public ProviderManager authManagerBean(HttpSecurity security, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        return (ProviderManager) security.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider).
                build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow only example.com as an allowed origin
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Allow only GET, POST, PUT, and DELETE methods
        config.setAllowCredentials(true);
        config.addAllowedHeader("*"); // Allow any header
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
