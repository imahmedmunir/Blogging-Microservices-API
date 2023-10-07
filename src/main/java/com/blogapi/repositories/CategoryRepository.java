package com.blogapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public Category findByCatName(String catName);

}
