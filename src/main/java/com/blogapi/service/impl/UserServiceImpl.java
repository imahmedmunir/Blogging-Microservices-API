package com.blogapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Role;
import com.blogapi.entities.User;
import com.blogapi.exception.CheckedException;
import com.blogapi.exception.SecurityException;
import com.blogapi.helper.BlogConstants;
import com.blogapi.payloads.UserDto;
import com.blogapi.repositories.RoleRepository;
import com.blogapi.repositories.UserRepository;
import com.blogapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDTo) {
		
		User user = dtoToUser(userDTo);
		
		Role role = roleRepository.findById(BlogConstants.ROLE_USER).get();
		user.getRoles().add(role);
		
		user.setPassword(bCryptPasswordEncoder.encode(userDTo.getPassword()));
	
		User saveUser = userRepo.save(user);
		
		System.out.println(user.getId()+" this is Id");
		
		return userToDto(saveUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto , Integer userId) {
			User user = userRepo.findById(userId)
					.orElseThrow(()-> new CheckedException("User", "Id", userId));
			/* this is checkException........... run time.*/
			
			System.out.println("dto "+userDto);
			
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setAbout(userDto.getAbout());
			this.userRepo.save(user);
			
			return this.userToDto(user);
	}

	
	@Override
	public List<UserDto> findAll() {
		
		List<User> allUsers = userRepo.findAll();
		
		List<UserDto> allDtos = allUsers.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		
		return allDtos;
	}

	@Override
	public void deleteUser(int userId) {
				User user = userRepo.findById(userId).orElseThrow(()-> new CheckedException("User" , "Id", userId));
				userRepo.delete(user);
	}
	
	
	//model mapper helps to map class of entity (user) to UserDto (transfer object...)
	public UserDto userToDto(User user) {
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}
	
	public User dtoToUser(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		return user;
	}

	@Override
	public UserDto findUserByEmail(String email) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new SecurityException("User ", "user", email));
			
			UserDto toDto = userToDto(user);
			return toDto;
		}

	}

