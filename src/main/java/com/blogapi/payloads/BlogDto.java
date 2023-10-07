package com.blogapi.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDto {

	private Integer postId;

	@NotNull
	@Size(min=5, message = "size must be at least 5 characters")
	private String title;
	
	@NotNull
	@Size(min = 10, message = "minimum size of your content must be 10")
	private String content;
	private String image;
	private Date date;

	// don't use entity instead user DTo classes
	/* use @JsonIgnoreCase if you don't want to see data of user adn category*/
	
	private UserDto user;

	// DTO classes not entity
	private CategoryDto category;
	
	private List<CommentDto> comments = new ArrayList<>();
	
	//private Set<CommentDto> comments = new HashSet<>();

}
