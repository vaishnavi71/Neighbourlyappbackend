package com.blogapp.payload;

import java.util.Set;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	private Set<String> role;
	
}