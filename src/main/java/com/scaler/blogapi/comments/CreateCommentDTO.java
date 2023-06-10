package com.scaler.blogapi.comments;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCommentDTO {
    String title;
    String body;
    String username;
}
