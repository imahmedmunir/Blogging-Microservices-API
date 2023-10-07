package com.blogapi.helper;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.blogapi.payloads.BlogDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogPagination {

	private List<BlogDto> content;
	
	private Integer pageNumber;
	private Integer pageSize;
	private long totalPages;
	private Long totalElements;
	private Boolean lastPage;
	
	

}
