package com.jschwery.securitydemo.service;


import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.service.Implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    UserModel inputUserModel;

    @BeforeEach
    public void setUp() throws Exception {
        inputUserModel = new UserModel();
        inputUserModel.setEmail("testemail@gmail.com");
        inputUserModel.setFirstName("john");
        inputUserModel.setLastName("doe");
        inputUserModel.setPassword("test");
        inputUserModel.setMatchPassword("test");

        User inputUser = User.builder()
                .email(inputUserModel.getEmail())
                .firstName(inputUserModel.getFirstName())
                .lastName(inputUserModel.getLastName())
                .password(inputUserModel.getPassword())
                .timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).userID(1)
                .build();

        User outputUser = User.builder().
                email(inputUserModel.getFirstName()).
                password(inputUserModel.getLastName()).
                lastName(inputUserModel.getFirstName())
                .firstName(inputUserModel.getFirstName())

                .timeCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()))).userID(1).build();

        Mockito.when(repository.save(inputUser)).thenReturn(outputUser);

    }

    @Test
    public void whenSaveUser_ThenUserHasID(){

        Assertions.assertEquals(1, userServiceImpl.saveUser(inputUserModel).getUserID());
    }
}
