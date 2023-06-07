package com.scaler.blogapi.blogs;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @GetMapping("")
    public String getAllBlogs(){
        return "Articles";
    }
     @GetMapping("/private")
    public String getAllPrivateBlogs(@AuthenticationPrincipal UUID id){
        return "Private Articles fetched for "+ id;
     }
}
