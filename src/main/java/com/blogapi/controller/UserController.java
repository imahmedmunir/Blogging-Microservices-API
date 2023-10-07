package com.blogapi.controller;

import java.util.List;

import javax.persistence.PrePersist;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.helper.ApiHelper;
import com.blogapi.payloads.UserDto;
import com.blogapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

		//update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto dto, @PathVariable("userId") Integer u_id) {
		UserDto updateUser = this.userService.updateUser(dto, u_id);
		return ResponseEntity.ok(updateUser);
	}

	//get all users
	@GetMapping("/") 
	public ResponseEntity<List<UserDto>> getAll(){ 
		return ResponseEntity.ok(this.userService.findAll());
	}

	
	//delete user
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<ApiHelper> delete(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiHelper("User with Id "+userId+" deleted", true), HttpStatus.OK);
	}
	
	//single user
	@GetMapping("/{email}")
	public ResponseEntity<UserDto> findOne(@PathVariable String email){
		
		UserDto dto = this.userService.findUserByEmail(email);
		return new ResponseEntity<>(dto, HttpStatus.OK);
		
	}
	

}
