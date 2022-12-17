package com.jschwery.securitydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.springframework.security.config.Customizer.withDefaults;

/*
if the user is an admin they can remove other users from db
plus all other features of users

HTTP request -> security filter chain
filter chain -> custom http login page
same thymeleaf html page has get and post available to post the form details?
the thymeleaf page should have the oauth google link too
if user clicks on the google link, provider manager




 */

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    //reads database info form properties
    @Autowired
    DataSource dataSource;

    public void authProvider1(AuthenticationProvider oAuthProvider){

    }

    /**
     * Authenticates with the specified database in the properties file
     * finds the user from the table by username we can get their information and find their roles to assign
     * proper authorization
     */
    @Bean
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication()
                .passwordEncoder(getPassWordEncoder())//spring security requires these columns to be "username, enabled, password & role"
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }

    @Bean
    public UserDetailsService userDetailsService(){
        //authenitcation providers extract user indentity info based credentials from database
        UserDetails user = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    //only used for the password because its only authentication where we store the data in the database
    //this implements authentication manager
    @Bean
    public ProviderManager getProviderManager(){
        return new ProviderManager();
    }
    //loop through

    //how do we go from the security filter chain to the provider manager

    //and then how do we set the provider manager to call different authentication providers


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()).
                formLogin(form -> form.loginPage("/login"));
        return http.build();
    }
}
