package com.scaler.blogapi.blogs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBlogDTO {
    String title;
    String subtitle;
    String slug;
    String body;
    String authorName;
}
