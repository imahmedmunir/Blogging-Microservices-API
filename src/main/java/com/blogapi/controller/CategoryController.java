package com.blogapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.helper.CategoryResponse;
import com.blogapi.payloads.CategoryDto;
import com.blogapi.service.impl.CategoryServicdImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
		
	@Autowired
	private CategoryServicdImpl servicdImpl;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCat(@Valid @RequestBody CategoryDto dto){
		CategoryDto createCat = servicdImpl.createCat(dto);
		return new ResponseEntity<>(createCat, HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCat(@Valid @RequestBody CategoryDto dto, @PathVariable Integer catId){
		CategoryDto updateCat = servicdImpl.updateCat(dto, catId);
		return new ResponseEntity<>(updateCat, HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getsingle(@PathVariable Integer catId){
		CategoryDto singleCat = this.servicdImpl.getSingleCat(catId);
		return ResponseEntity.ok(singleCat);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCat(){
		List<CategoryDto> allCat = this.servicdImpl.getAllCat();
		
		return ResponseEntity.ok(allCat);
	}
	
	@DeleteMapping("/{catId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryResponse> delete(@PathVariable Integer catId){
			this.servicdImpl.deleteCat(catId);
			
			return new ResponseEntity<>(new CategoryResponse("Category with Id "+catId+" deleted", true), HttpStatus.OK);
	}
}
