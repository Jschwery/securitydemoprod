//package com.jschwery.securitydemo.service;
//
//
//import com.jschwery.securitydemo.entities.User;
//import com.jschwery.securitydemo.model.UserModel;
//import com.jschwery.securitydemo.repository.UserRepository;
//import com.jschwery.securitydemo.service.Implementations.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository repository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @InjectMocks
//    private UserServiceImpl userServiceImpl;
//
//    @BeforeEach
//    public void setUp(){
//    }
//
//    @Test
//    void saveUser_userHasID(){
//        // Arrange
//        final UserModel inputUserModel = new UserModel();
//        inputUserModel.setEmail("testemail@gmail.com");
//        inputUserModel.setFirstName("john");
//        inputUserModel.setLastName("doe");
//        inputUserModel.setPassword(passwordEncoder.encode("test"));
//        inputUserModel.setMatchPassword("test");
//        System.out.println(inputUserModel.getPassword());
//        when(repository.save(any(User.class))).thenAnswer(invocation -> {
//            final User entity = invocation.getArgument(0);
//            ReflectionTestUtils.setField(entity, "userID", 1);
//            return entity;
//        });
//
//
//        final Optional<User> user = userServiceImpl.saveUser(inputUserModel);
//
//        assertThat(user.get().getUserID()).isEqualTo(1);
//
//    }
//}