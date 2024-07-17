package com.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.config.AppConstants;
import com.blogapp.dto.AdminDto;
import com.blogapp.dto.AdminResponseDto;
import com.blogapp.dto.UserDto;
import com.blogapp.dto.UserResponseDto;
import com.blogapp.entity.Role;
import com.blogapp.entity.User;
import com.blogapp.exception.UserAlreadyExistException;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.repository.RoleRepository;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public UserResponseDto registerNewUser(final UserDto userDto) throws UserAlreadyExistException {
		User user = this.modelMapper.map(userDto, User.class);
		if (this.userRepo.findByEmail(user.getEmail()).isPresent()) {
			throw new UserAlreadyExistException("User already exist for email ID: " + user.getEmail());
		} else {
			// System.out.println(this.passwordEncoder.encode(user.getPassword()));
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			// System.out.println(user.getRoles());

			user.getRoles().add(role);

			User newUser = this.userRepo.save(user);
			return this.modelMapper.map(newUser, UserResponseDto.class);
		}
	}

	

//	@Override
//	public UserLoginDto createUser(UserLoginDto userDto) throws UserAlreadyExistException {
//		User user = this.dtoToUser(userDto);
//		System.out.println(user);
//		if (this.userRepo.findByEmail(user.getEmail()).isPresent()) {
//			throw new UserAlreadyExistException("User already exist for email ID: " + user.getEmail());
//		} else {
//			User savedUser = this.userRepo.save(user);
//			return this.userToDto(savedUser);
//		}
//	}

	@Override
	public UserResponseDto updateUser(final UserResponseDto updateUserDto, final Long userId)
			throws UserNotFoundException {
//
//		if (this.userRepo.findByEmail(updateUserDto.getEmail()).isPresent()) {
//			throw new UserAlreadyExistException("User already exist for email ID: " + updateUserDto.getEmail());
//		}
//		else {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for userId: " + userId));
		user.setFirstName(updateUserDto.getFirstName());
		user.setLastName(updateUserDto.getLastName());
		user.setEmail(updateUserDto.getEmail());

		User updatedUser = userRepo.save(user);
		return this.userToDto(updatedUser);
//		}
	}

	@Override
	public UserResponseDto getUserById(final Long userId) throws UserNotFoundException {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for user ID: " + userId));
		return this.modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto getUserByEmail(final String email) throws UserNotFoundException {
		User user = this.userRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found for email ID: " + email));
		return this.modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
		List<User> users = this.userRepo.findAll();

		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}

		List<UserResponseDto> userDtos = users.stream().map(this::userToDto).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(final Long userId) throws UserNotFoundException {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for userId: " + userId));
		user.getFeedback().clear();
		user.getRoles().clear();
		userRepo.deleteById(userId);
//		this.userRepo.delete(user);
		
	}

	private UserResponseDto userToDto(final User user) {
		UserResponseDto userDto = this.modelMapper.map(user, UserResponseDto.class);
		return userDto;
	}
	@Override
    public AdminResponseDto registerNewAdmin(final AdminDto adminDto) throws UserAlreadyExistException {
        User admin = this.modelMapper.map(adminDto, User.class);
        if (this.userRepo.findByEmail(admin.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User already exists for email ID: " + admin.getEmail());
        } else {
            admin.setPassword(this.passwordEncoder.encode(admin.getPassword()));
            Role adminRole = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
            admin.getRoles().add(adminRole);

 

            User newAdmin = this.userRepo.save(admin);
            return this.modelMapper.map(newAdmin, AdminResponseDto.class);
        }
    }

}