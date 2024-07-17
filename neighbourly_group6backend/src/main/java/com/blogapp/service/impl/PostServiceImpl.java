package com.blogapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.blogapp.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blogapp.dto.PostDto;
import com.blogapp.dto.PostResponse;
import com.blogapp.entity.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.repository.PostRepository;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

//	public PostServiceImpl(ModelMapper mapper, PostRepository postRepository) {
//		this.mapper = mapper;
//		this.postRepository = postRepository;
//	}

	@Override
	public PostDto createPost(PostDto postDto, Long id) throws UserNotFoundException {

		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found for userId: " + id));

		Post feedback = mapToEntity(postDto);
		feedback.setUser(user);
		Post newFeedback = postRepository.save(feedback);
		PostDto postResponse = mapToDto(newFeedback);
		return postResponse;
	}

	private PostDto mapToDto(Post feedback) {

		PostDto postDto = mapper.map(feedback, PostDto.class);

		return postDto;
	}

	private Post mapToEntity(PostDto postDto) {

		Post feedback = mapper.map(postDto, Post.class);

		return feedback;
	}
	
	@Override
    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }



	@Override
	public PostDto getPostById(long id) {
		Post feedback = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(feedback);
	}

	@Override
	public void deletePost(long id) {
		Post feedback = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(feedback);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post feedback = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        feedback.setTitle(postDto.getTitle());
		feedback.setContent(postDto.getContent());
		feedback.setCategory(postDto.getCategory());
        feedback.setCreationTime(String.valueOf(LocalDateTime.now()));
		Post updatedFeedback = postRepository.save(feedback);
		return mapToDto(updatedFeedback);
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = postRepository.findAll(pageable);

		List<Post> listOfFeedbacks = posts.getContent();

		List<PostDto> content = listOfFeedbacks.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

}