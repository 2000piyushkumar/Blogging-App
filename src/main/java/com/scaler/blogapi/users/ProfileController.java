package com.scaler.blogapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getAllProfiles(){
        return ResponseEntity.ok(userService.getAllProfiles());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getProfileByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getProfileByUsername(username));
    }
}
