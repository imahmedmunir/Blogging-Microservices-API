package com.blogapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogapi.entities.Role;
import com.blogapi.helper.BlogConstants;
import com.blogapi.repositories.RoleRepository;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
	
	@Bean //method level annotation used to get object of mapper
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		Role admin = new Role();
		admin.setId(BlogConstants.ROLE_ADMIN);
		admin.setRole("ROLE_ADMIN");
		
		this.roleRepository.save(admin);
		
		Role user = new Role();
		
		user.setId(BlogConstants.ROLE_USER);
		user.setRole("ROLE_USER");
		
		this.roleRepository.save(user);
	}

}
