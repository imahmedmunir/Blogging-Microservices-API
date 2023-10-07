package com.blogapi.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Integer catId;
	
	@NotBlank(message = "Choose a proper category name")
	@Size(min = 5)
	private String catName;
	
	@NotBlank
	private String catDescription;
	
}
