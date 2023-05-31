package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.Exception.DuplicateEmailException;
import com.scaler.blogapi.commons.Exception.UserAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistException, DuplicateEmailException {
        if(userRepository.findByUsername(createUserDTO.getUserName())!=null){
            throw new UserAlreadyExistException(createUserDTO.getUserName());
        }
        if(userRepository.findByEmail(createUserDTO.getEmail())!=null){
            throw new DuplicateEmailException(createUserDTO.getEmail());
        }

        UserEntity newUserEntity = modelMapper.map(createUserDTO,UserEntity.class);
        UserEntity savedUser = userRepository.save(newUserEntity);
        return modelMapper.map(savedUser,UserResponseDTO.class);
    }

    public UserResponseDTO loginUser(LoginUserDTO loginUserDTO){
        UserEntity userEntity = userRepository.findByUsernameAndPassword(loginUserDTO.getUserName(),loginUserDTO.getPassword());
        return modelMapper.map(userEntity,UserResponseDTO.class);
    }
}
