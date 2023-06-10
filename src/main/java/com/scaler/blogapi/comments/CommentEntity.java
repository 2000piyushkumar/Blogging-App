package com.scaler.blogapi.comments;

import com.scaler.blogapi.blogs.BlogEntity;
import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.*;

@Setter
@Getter
@Entity(name = "Comments")
public class CommentEntity extends BaseEntity {
    @Column(nullable = false,length = 150)
    String title;
    @Column(nullable = false,length = 600)
    String body;
    @ManyToOne
    UserEntity authorComment;
    @ManyToOne
    BlogEntity blog;
}
