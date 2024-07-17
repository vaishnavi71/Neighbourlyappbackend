package com.blogapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.dto.UserResponseDto;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

//	@PostMapping("/signup")
//	public ResponseEntity<UserLoginDto> createUser(@Valid@RequestBody UserLoginDto userDto) throws UserAlreadyExistException {
//		return new ResponseEntity<UserLoginDto>(this.userService.createUser(userDto), HttpStatus.OK);
//	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody final UserResponseDto updateUserDto,
			@PathVariable("userId") final Long userId) throws UserNotFoundException {
//		System.out.println("in side controller" + updateUserDto);
		return new ResponseEntity<UserResponseDto>(this.userService.updateUser(updateUserDto, userId), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable final Long userId) throws UserNotFoundException {
		return new ResponseEntity<UserResponseDto>(this.userService.getUserById(userId), HttpStatus.OK);
	}

	@GetMapping("/by-email/{email}")
	public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable("email") final String email)
			throws UserNotFoundException {
		return new ResponseEntity<UserResponseDto>(this.userService.getUserByEmail(email), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUsers() throws UserNotFoundException {
		return new ResponseEntity<List<UserResponseDto>>(this.userService.getAllUsers(), HttpStatus.OK);
	}

	// ADMIN
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity deleteUser(@PathVariable Long userId) throws UserNotFoundException {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}