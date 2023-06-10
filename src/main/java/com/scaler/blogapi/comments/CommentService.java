package com.scaler.blogapi.comments;

import com.scaler.blogapi.blogs.BlogEntity;
import com.scaler.blogapi.blogs.BlogRepository;
import com.scaler.blogapi.users.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    public final CommentRepository commentRepository;
    public final BlogRepository blogRepository;
    public final UserRepository userRepository;
    public final ModelMapper modelMapper;
    @Autowired
    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<ResponseCommentDTO> getAllCommentsForBlog(UUID blogId){
        BlogEntity blogEntity = blogRepository.findById(blogId).get();
        return commentRepository.findAll().stream().filter((comment)->{
            return comment.getBlog().getId()== blogId;
        }).map((comment)-> {
            ResponseCommentDTO responseCommentDTO = modelMapper.map(comment,ResponseCommentDTO.class);
            responseCommentDTO.setAuthorName(comment.getAuthorComment().getUsername());
            responseCommentDTO.setBlogSlug(comment.getBlog().getSlug());
            return responseCommentDTO;
        }).toList();
    }

    public ResponseCommentDTO createCommentForBlog(UUID blogId, CreateCommentDTO createCommentDTO){
        CommentEntity commentEntity = modelMapper.map(createCommentDTO, CommentEntity.class);
        commentEntity.setAuthorComment(userRepository.findByUsername(createCommentDTO.getUsername()));
        commentEntity.setBlog(blogRepository.findById(blogId).get());
        CommentEntity savedCommentEntity = commentRepository.save(commentEntity);
        ResponseCommentDTO responseCommentDTO = modelMapper.map(savedCommentEntity, ResponseCommentDTO.class);
        responseCommentDTO.setAuthorName(savedCommentEntity.getAuthorComment().getUsername());
        responseCommentDTO.setBlogSlug(savedCommentEntity.getBlog().getSlug());
        return responseCommentDTO;
    }

    public ResponseCommentDTO deleteCommentForBlog(UUID commentId){
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        commentRepository.deleteById(commentId);
        ResponseCommentDTO responseCommentDTO = modelMapper.map(commentEntity,ResponseCommentDTO.class);
        responseCommentDTO.setAuthorName(commentEntity.getAuthorComment().getUsername());
        responseCommentDTO.setBlogSlug(commentEntity.getBlog().getSlug());
        return responseCommentDTO;
    }
}
