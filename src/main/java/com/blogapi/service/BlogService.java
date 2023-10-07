package com.blogapi.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blogapi.helper.BlogPagination;
import com.blogapi.payloads.BlogDto;

public interface BlogService {
	//creating blog	
	BlogDto createBlog(BlogDto dto, Integer userId, String  categoryName) throws Exception;
		 
	//updating blog
	BlogDto updateBlog(BlogDto dto, Integer blogId);
	
	//deleting blog
	void deleteBlog(Integer blogId);
	
	//get single blog
	BlogDto getOne(Integer blogId);
	
	//get all blogs
	BlogPagination getAll(int pageN, int pageSize, String sortBy, String sortType);
	
	//get blogs by user 
	List<BlogDto> getByUser(Integer userId);
	
	
	//get blogs by category
	List<BlogDto> getByCategory(Integer catId);
	
	List<BlogDto> searchPost(String keyword);
	
	String uploadImage(String path , MultipartFile file) throws IOException;
	
	InputStream serveFile(String path, String fileName) throws FileNotFoundException;
}
