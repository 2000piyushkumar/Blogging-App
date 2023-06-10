package com.scaler.blogapi.blogs;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseBlogDTO> createBlog(@RequestBody CreateBlogDTO createBlogDTO){
        return ResponseEntity.ok(blogService.createBlog(createBlogDTO));
    }

    @PatchMapping("/{blog_slug}")
    public ResponseEntity<ResponseBlogDTO> updateBlog(@RequestBody HashMap<BlogService.BlogUpdateParams,String> blogUpdateMap, @PathVariable String blog_slug ){
        return ResponseEntity.ok(blogService.updateBlog(blog_slug,blogUpdateMap));
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseBlogDTO>> getAllBlogs(@RequestParam Integer size,@RequestParam Integer page){
        if(size == null ) size= 10;
        if(page == null) page = 0;
        return ResponseEntity.ok(blogService.getAllBlogs(size,page));
    }

    @GetMapping("/{blog_slug}")
    public ResponseEntity<ResponseBlogDTO> getBlogBySlug(@PathVariable String blog_slug){
        return ResponseEntity.ok(blogService.getBlogBySlug(blog_slug));
    }
}
