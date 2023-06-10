package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.Exception.DuplicateEmailException;
import com.scaler.blogapi.commons.Exception.UserAlreadyExistException;
import com.scaler.blogapi.commons.Exception.IllegalArgumentException;
import com.scaler.blogapi.security.authTokens.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthTokenService authTokenService;
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JWTService jwtService, AuthTokenService authTokenService) {

        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }

    public UserResponseDTO createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistException, DuplicateEmailException {
        if(userRepository.findByUsername(createUserDTO.getUserName())!=null){
            throw new UserAlreadyExistException(createUserDTO.getUserName());
        }
        if(userRepository.findByEmail(createUserDTO.getEmail())!=null){
            throw new DuplicateEmailException(createUserDTO.getEmail());
        }

        UserEntity newUserEntity = modelMapper.map(createUserDTO,UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        UserEntity savedUser = userRepository.save(newUserEntity);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser,UserResponseDTO.class);
        userResponseDTO.setToken(jwtService.createJWT(savedUser.getId()));
        return userResponseDTO;
    }

    public UserResponseDTO loginUser(LoginUserDTO loginUserDTO, AuthType authType) throws IllegalArgumentException {
        UserEntity userEntity = userRepository.findByUsername(loginUserDTO.getUserName());
        if(userEntity == null){
            throw new IllegalArgumentException();
        }
        var passMatch = passwordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword());
        if(!passMatch){
            throw new IllegalArgumentException();
        }
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity,UserResponseDTO.class);
        switch (authType){
            case JWT:
                userResponseDTO.setToken(jwtService.createJWT(userEntity.getId()));
                break;
            case AUTH_TOKEN:
                userResponseDTO.setToken(authTokenService.createAuthToken(userEntity).toString());
        }

        return userResponseDTO;
    }

    public List<UserResponseDTO> getAllProfiles(){
        return userRepository.findAll().stream().map((user)->{
            return modelMapper.map(user,UserResponseDTO.class);
        }).toList();
    }

    public UserResponseDTO getProfileByUsername(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        return modelMapper.map(userEntity,UserResponseDTO.class);
    }
    public static enum AuthType{
        JWT,
        AUTH_TOKEN
    }
}
