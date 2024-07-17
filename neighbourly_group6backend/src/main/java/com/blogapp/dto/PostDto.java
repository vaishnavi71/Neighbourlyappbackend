package com.blogapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private Long id;
	@NotEmpty
    @Size(min=4,message="Please add your title")
	private String title;
	@NotNull(message = "Please add your content")
	private String content;
	
	@NotNull(message = "please specify category of your post")
	private String category;
	private String creationTime;
	//private Set<Comment> comments;
	
}