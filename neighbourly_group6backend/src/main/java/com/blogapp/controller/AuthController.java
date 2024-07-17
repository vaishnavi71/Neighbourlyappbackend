package com.blogapp.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.dto.AdminDto;
import com.blogapp.dto.AdminResponseDto;
import com.blogapp.dto.UserDto;
import com.blogapp.dto.UserResponseDto;
import com.blogapp.exception.UserAlreadyExistException;
import com.blogapp.payload.JwtAuthRequest;
import com.blogapp.payload.JwtAuthResponse;
import com.blogapp.security.JwtTokenHelper;
import com.blogapp.service.UserService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsSerrvice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody final JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsSerrvice.loadUserByUsername(request.getUsername());
		Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setRole(roles);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(final String username, final String password) throws Exception {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid details !!");
			throw new Exception("Invalid username and password");
		}

	}

	// register new user
	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody final UserDto userDto)
			throws UserAlreadyExistException {
		// System.out.println(userDto);
		UserResponseDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserResponseDto>(registeredUser, HttpStatus.CREATED);
	}
	@PostMapping("/signup/admin")
    public ResponseEntity<AdminResponseDto> registerAdmin(@Valid @RequestBody final AdminDto adminDto)
            throws UserAlreadyExistException {
        AdminResponseDto registeredAdmin = this.userService.registerNewAdmin(adminDto);
        return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    }
	
}