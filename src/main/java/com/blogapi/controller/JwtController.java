package com.blogapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.Cofig.CustomUserDetailsService;
import com.blogapi.exception.SecurityException;
import com.blogapi.jwtConfig.JwtRequest;
import com.blogapi.jwtConfig.JwtResponse;
import com.blogapi.jwtConfig.JwtToken;
import com.blogapi.payloads.UserDto;
import com.blogapi.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class JwtController {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtToken jwtToken;
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	//creating user
		@PostMapping("/register")
		public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {
			
			UserDto createdDto = userService.createUser(dto);
			
			return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
		}
		
		@PostMapping("/login")
		public JwtResponse login(@RequestBody JwtRequest request){

			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
			
			try {
				
				this.authenticationManager.authenticate(authenticationToken);
				
			} catch (BadCredentialsException e) {
				throw new SecurityException("Invalid User name or password !! "+ e);
			}
			
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(request.getEmail());
		
			String generateToken = this.jwtToken.generateToken(userDetails);
			
			JwtResponse response = new JwtResponse();
			response.setToken(generateToken);
			
			return response;
			
			
		}
		
}
