package com.scaler.blogapi.blogs;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Blogs")
public class BlogEntity extends BaseEntity {
    @Column(nullable = false, unique = true,length = 150)
    String title;
    @Column(nullable = false,length = 100)
    String subtitle;
    @Column(nullable = false,unique = true, length = 150)
    String slug;
    @Column(nullable = false,length = 5000)
    String body;
    @ManyToOne
    UserEntity authorBlog;
    @ManyToMany
    List<UserEntity> likedBy;
}
