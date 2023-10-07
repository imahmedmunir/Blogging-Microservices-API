package com.blogapi.Cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapi.entities.User;
import com.blogapi.exception.SecurityException;
import com.blogapi.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userrepo.findByEmail(username)
				.orElseThrow(() -> new SecurityException("User ", "user ", username));
		
		
		return user;
	}

	
}
