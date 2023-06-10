package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.Exception.DuplicateEmailException;
import com.scaler.blogapi.commons.Exception.IllegalArgumentException;
import com.scaler.blogapi.commons.Exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) throws UserAlreadyExistException, DuplicateEmailException {
        return ResponseEntity.ok(userService.createUser(createUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO, @RequestParam(required = false, value = "token") String token) throws IllegalArgumentException {
        //if token == "JWT" then generate jwtToken and if token == "auth_token" then generate auth token
        var authType = UserService.AuthType.JWT;
        if(token != null && token.equals("auth_token")){
            authType = UserService.AuthType.AUTH_TOKEN;
        }
        return ResponseEntity.ok(userService.loginUser(loginUserDTO,authType));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
