package com.scaler.blogapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity findByUsernameAndPassword(String username,String password);

    Optional<UserEntity> findById(UUID id);
}
