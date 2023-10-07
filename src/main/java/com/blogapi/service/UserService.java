package com.blogapi.service;

import java.util.List;

import com.blogapi.payloads.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto userDTo);
	public UserDto updateUser(UserDto dto, Integer userId);
	public UserDto findUserByEmail(String email);
	public List<UserDto> findAll();
	public void deleteUser(int userId);
	
}
