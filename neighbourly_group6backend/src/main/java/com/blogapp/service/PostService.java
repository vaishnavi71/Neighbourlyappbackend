package com.blogapp.service;

import java.util.List;

import com.blogapp.dto.PostDto;
import com.blogapp.dto.PostResponse;
import com.blogapp.entity.Post;
import com.blogapp.exception.UserNotFoundException;

public interface PostService {

	public PostDto createPost(PostDto postDto, Long id) throws UserNotFoundException ;
	
	public PostDto getPostById(long id);
	
	public void deletePost(long id);
	
	public PostDto updatePost(PostDto postDto, long id);
	
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	public List<Post> getAllPosts();




}