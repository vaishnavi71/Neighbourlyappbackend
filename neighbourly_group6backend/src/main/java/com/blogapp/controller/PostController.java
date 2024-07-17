package com.blogapp.controller;

 

import java.util.List;

import javax.validation.Valid;


import com.blogapp.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 

import com.blogapp.dto.PostDto;
import com.blogapp.dto.PostResponse;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.service.PostService;
import com.blogapp.utils.AppConstants;

 
@CrossOrigin(origins="http://localhost:4200/")
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService postService;


 

    @PostMapping("/post/{id}")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto,@PathVariable Long id) throws UserNotFoundException {
        System.out.println(postDto);
        return new ResponseEntity<>(postService.createPost(postDto, id), HttpStatus.CREATED);
    }
    
    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPosts(){
        return new ResponseEntity<List<Post>>(postService.getAllPosts(),HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted Successfully!", HttpStatus.OK);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

}