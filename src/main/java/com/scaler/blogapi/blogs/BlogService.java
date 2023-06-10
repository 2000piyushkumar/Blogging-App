package com.scaler.blogapi.blogs;

import com.scaler.blogapi.users.UserEntity;
import com.scaler.blogapi.users.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.lang.System.in;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserRepository userRepository,ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseBlogDTO createBlog(CreateBlogDTO createBlogDTO){
        String authorName = createBlogDTO.authorName;
        BlogEntity blogEntity = modelMapper.map(createBlogDTO,BlogEntity.class);
        UserEntity userEntity = userRepository.findByUsername(authorName);
        blogEntity.setAuthorBlog(userEntity);
        BlogEntity savedBlogEntity = blogRepository.save(blogEntity);
        ResponseBlogDTO responseBlogDTO = modelMapper.map(savedBlogEntity, ResponseBlogDTO.class);
        responseBlogDTO.setAuthorName(savedBlogEntity.getAuthorBlog().getUsername());
        return responseBlogDTO;
    }

    public ResponseBlogDTO updateBlog(String blog_slug, HashMap<BlogUpdateParams,String> blogUpdateMap){
        BlogEntity blogEntity = blogRepository.findAll().stream().filter((blog)->{
            return blog.slug ==blog_slug;
        }).findFirst().get();
        for(BlogUpdateParams param : blogUpdateMap.keySet()){
            if(param == BlogUpdateParams.TITLE){
                blogEntity.setTitle(blogUpdateMap.get(param));
            }
            else if(param == BlogUpdateParams.SUBTITLE){
                blogEntity.setSubtitle(blogUpdateMap.get(param));
            }
            else if(param == BlogUpdateParams.SLUG){
                blogEntity.setSlug(blogUpdateMap.get(param));
            }
            else if(param == BlogUpdateParams.BODY){
                blogEntity.setBody(blogUpdateMap.get(param));
            }
        }
        blogRepository.deleteById(blogEntity.getId());
        blogRepository.save(blogEntity);
        ResponseBlogDTO responseBlogDTO = modelMapper.map(blogEntity,ResponseBlogDTO.class);
        responseBlogDTO.setAuthorName(blogEntity.getAuthorBlog().getUsername());
        return responseBlogDTO;
    }

    public List<ResponseBlogDTO> getAllBlogs(int size, int page){
        return blogRepository.findAll(PageRequest.of(page, size)).stream().map((blog)->{
            ResponseBlogDTO responseBlogDTO = modelMapper.map(blog,ResponseBlogDTO.class);
            responseBlogDTO.setAuthorName(blog.getAuthorBlog().getUsername());
            return responseBlogDTO;
        }).toList();
    }

    public ResponseBlogDTO getBlogBySlug(String blog_slug){
        BlogEntity blogEntity = blogRepository.findAll().stream().filter((blog)->{
            return blog.getSlug().equals(blog_slug);
        }).findFirst().get();
        ResponseBlogDTO responseBlogDTO = modelMapper.map(blogEntity,ResponseBlogDTO.class);
        responseBlogDTO.setAuthorName(blogEntity.getAuthorBlog().getUsername());
        return responseBlogDTO;
    }

    public static enum BlogUpdateParams{
        TITLE,
        SUBTITLE,
        SLUG,
        BODY
    }
}
