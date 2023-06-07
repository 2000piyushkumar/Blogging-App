package com.scaler.blogapi.users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    String userName;
    String email;
    @Column(nullable=false,length=256)
    String password;
    String bio;
}
