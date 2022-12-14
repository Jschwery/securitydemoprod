package com.jschwery.securitydemo.service;


import com.jschwery.securitydemo.entities.User;
import com.jschwery.securitydemo.model.UserModel;
import com.jschwery.securitydemo.repository.UserRepository;
import com.jschwery.securitydemo.service.Implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void saveUser_userHasID(){
        // Arrange
        final UserModel inputUserModel = new UserModel();
        inputUserModel.setEmail("testemail@gmail.com");
        inputUserModel.setFirstName("john");
        inputUserModel.setLastName("doe");
        inputUserModel.setPassword("test");
        inputUserModel.setMatchPassword("test");

        when(repository.save(any(User.class))).thenAnswer(invocation -> {
            final User entity = invocation.getArgument(0);
            ReflectionTestUtils.setField(entity, "userID", 1);
            return entity;
        });

        final User user = userServiceImpl.saveUser(inputUserModel);
        System.out.println(user.getUserID());

        assertThat(user.getUserID()).isEqualTo(1);

    }
}