package com.blogapp.service;

import java.util.List;

import com.blogapp.dto.AdminDto;
import com.blogapp.dto.AdminResponseDto;
import com.blogapp.dto.UserDto;
import com.blogapp.dto.UserResponseDto;
import com.blogapp.exception.UserAlreadyExistException;
import com.blogapp.exception.UserNotFoundException;

public interface UserService {
	// Create User
//	UserLoginDto createUser(UserLoginDto userDto) throws UserAlreadyExistException;
	// Update User
	UserResponseDto updateUser(UserResponseDto updateUserDto, Long userId) throws UserNotFoundException;

	// Get User By Id
	UserResponseDto getUserById(Long userId) throws UserNotFoundException;

	// Get User By Email
	UserResponseDto getUserByEmail(String email) throws UserNotFoundException;

	// Get all Users
	List<UserResponseDto> getAllUsers() throws UserNotFoundException;
	
	AdminResponseDto registerNewAdmin(final AdminDto adminDto) throws UserAlreadyExistException;

	// Delete User
	void deleteUser(Long userId) throws UserNotFoundException;

	UserResponseDto registerNewUser(UserDto userDto) throws UserAlreadyExistException;

	
}
