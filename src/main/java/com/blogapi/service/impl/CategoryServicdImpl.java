package com.blogapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Category;
import com.blogapi.exception.CheckedException;
import com.blogapi.payloads.CategoryDto;
import com.blogapi.repositories.CategoryRepository;
import com.blogapi.service.CategoryService;

@Service
public class CategoryServicdImpl implements CategoryService {

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCat(CategoryDto catDto) {
		Category category = mapper.map(catDto, Category.class);
		
		Category savedCat = catRepo.save(category);
		
		return mapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCat(CategoryDto dto, int Id) {
		Category category = catRepo.findById(Id)
				.orElseThrow(() -> new CheckedException("Category", "Category Id", Id));
		category.setCatName(dto.getCatName());
		category.setCatDescription(dto.getCatDescription());
		Category updatedCat = catRepo.save(category);
		return mapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCat(Integer Id) {
			Category category = catRepo.findById(Id)
					.orElseThrow(()-> new CheckedException("Category" , "category Id ", Id));
			
			catRepo.delete(category);
	}

	@Override
	public CategoryDto getSingleCat(Integer catId) {
			Category category = catRepo.findById(catId)
					.orElseThrow(()-> new CheckedException("Category", "category Id " , catId));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCat() {
			List<Category> listCat = catRepo.findAll();
			
			//with loop
			List<CategoryDto> listtoDto = new ArrayList<CategoryDto>();
			for (Category category : listCat) {
				listtoDto.add(mapper.map(category, CategoryDto.class));
			}
			
			//with lambda expression
			//List<CategoryDto> list = listCat.stream().map((cat)-> mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return listtoDto;
	}

}
