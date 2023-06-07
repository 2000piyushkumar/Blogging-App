package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.blogs.BlogEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false,unique = true,length = 30)
    String username;
    @Column(nullable = false,unique = true,length = 50)
    String email;
    @Column(nullable = false,length = 256)
    String password;
    @Column(length = 100)
    String bio;
    @ManyToMany(mappedBy = "likedBy")
    List<BlogEntity> blogsLiked;
    @ManyToMany
    @JoinTable(
          name = "follower_following",
          joinColumns = @JoinColumn(name = "follower_id"),
          inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    List<UserEntity> following;
    @ManyToMany(mappedBy = "following")
    List<UserEntity> followers;
}
