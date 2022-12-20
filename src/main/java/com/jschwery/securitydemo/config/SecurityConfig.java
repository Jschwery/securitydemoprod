package com.jschwery.securitydemo.config;

import com.jschwery.securitydemo.repositories.UserRepository;
import com.jschwery.securitydemo.security.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.springframework.security.config.Customizer.withDefaults;

/*
//TODO
if the user is an admin they can remove other users from db
plus all other features of users

//TODO
create roles everyone starts as user when account created
securityfilterchain ->filters http requests and finds out if the user is authorized to access the endpoints
providermanager -> find out how to manage the different authentication providers with their authorization
userdetailsservice -> create user
 */

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    UserRepository userRepository;
    CustomSecurityConfig customProviderConfig;

    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    public UserDetailsManager usersManager(DataSource dataSource){
        UserDetails user = User.builder()
                .username()
    }

    @Bean
    public AuthenticationManager authManagerBean(UserDetailsService userDetailsService, HttpSecurity security,
   BCryptPasswordEncoder passwordEncoder) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login/**", "/css/**", "/js/**", "/registration/**").permitAll()
                .anyRequest().authenticated())
                .csrf().disable()
                .formLogin(form -> form.loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureForwardUrl("/login-failure?error=true"))
                .logout().permitAll();
        return http.build();
    }

}
