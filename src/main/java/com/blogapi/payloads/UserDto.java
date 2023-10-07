package com.blogapi.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	/*   we're not exposing our entities , that's why we're using DTO. Mention only properties which you want 
	 * to take as input from user  */
	
	private Integer id;
	
	@NotNull
	@Size(min = 5, max=15 )
	private String name;
	
	@NotNull
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.com")
	private String email;
	
	@Size(min = 8, max = 15)
	private String password;
	
	@NotBlank
	private String about;
	
}
