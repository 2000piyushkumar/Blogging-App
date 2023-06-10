package com.scaler.blogapi.comments;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ResponseCommentDTO {
    UUID id;
    String title;
    String body;
    String authorName;
    String blogSlug;
}
