package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.Exception.DuplicateEmailException;
import com.scaler.blogapi.commons.Exception.UserAlreadyExistException;
import com.scaler.blogapi.security.authTokens.AuthTokenRepository;
import com.scaler.blogapi.security.authTokens.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserServiceTest {
    @Autowired private UserRepository userRepository;
    @Autowired private AuthTokenRepository authTokenRepository;
    public UserService createUserService(){
        return new UserService(userRepository,new ModelMapper(), new BCryptPasswordEncoder(),new JWTService(), new AuthTokenService(authTokenRepository));
    }

    @Test
    public void testCreateUser() throws UserAlreadyExistException, DuplicateEmailException {
        UserService userService = createUserService();
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUserName("Test");
        createUserDTO.setPassword("xyz");
        createUserDTO.setEmail("test@gmail.com");
        createUserDTO.setBio("for testing purpose");
        UserResponseDTO userResponseDTO = userService.createUser(createUserDTO);
        assertNotNull(userResponseDTO);
        Assertions.assertEquals(userResponseDTO.getUserName(),"Test");
        Assertions.assertEquals(userResponseDTO.getEmail(),"test@gmail.com");
    }
}
