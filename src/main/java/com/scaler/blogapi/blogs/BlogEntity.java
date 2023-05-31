package com.scaler.blogapi.blogs;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;

import java.util.*;

@Entity(name = "Blogs")
public class BlogEntity extends BaseEntity {
    @Column(nullable = false,length = 150)
    String title;
    @Column(nullable = false,length = 100)
    String subtitle;
    @Column(nullable = false,length = 150)
    String slug;
    @Column(nullable = false,length = 5000)
    String body;
    @ManyToOne
    UserEntity authorBlog;
    @ManyToMany
    List<UserEntity> likedBy;
}
