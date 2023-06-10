package com.scaler.blogapi.security.authTokens;

import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "auth_tokens")
@Getter
@Setter
public class AuthTokenEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
