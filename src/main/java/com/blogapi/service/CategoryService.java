package com.blogapi.service;

import java.util.List;

import com.blogapi.payloads.CategoryDto;

public interface CategoryService {
	
		CategoryDto createCat(CategoryDto dto);
		CategoryDto updateCat(CategoryDto dto, int Id);
		void deleteCat(Integer Id);
		CategoryDto getSingleCat(Integer id);
		List<CategoryDto> getAllCat();
}
