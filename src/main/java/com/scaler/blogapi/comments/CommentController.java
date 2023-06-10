package com.scaler.blogapi.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blogs/{blogId}/comments")
public class CommentController {
    public final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseCommentDTO>> getAllCommentsForBlog(@PathVariable UUID blogId){
        return ResponseEntity.ok(commentService.getAllCommentsForBlog(blogId));
    }

    @PostMapping("")
    public ResponseEntity<ResponseCommentDTO> createCommentForBlog(@PathVariable UUID blogId,@RequestBody CreateCommentDTO createCommentDTO){
        return ResponseEntity.ok(commentService.createCommentForBlog(blogId,createCommentDTO));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDTO> deleteCommentFromBlog(@PathVariable UUID blogId,@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.deleteCommentForBlog(commentId));
    }
}
