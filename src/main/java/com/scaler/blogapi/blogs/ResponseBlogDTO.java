package com.scaler.blogapi.blogs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseBlogDTO {
    UUID id;
    String title;
    String subtitle;
    String authorName;
}
