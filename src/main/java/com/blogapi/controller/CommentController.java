package com.blogapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.helper.CategoryResponse;
import com.blogapi.payloads.CommentDto;
import com.blogapi.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService service;
	
	@PostMapping("blog/{blogId}/user/{userId}")
	public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentDto dto, 
																@PathVariable Integer blogId, @PathVariable Integer userId) {
		
		CommentDto commentDto = service.addComment(dto, blogId, userId);
		
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("comment/{commentId}")
	public ResponseEntity<CategoryResponse> deleteComm(@PathVariable Integer commentId){
		service.deleteCom(commentId);
		
		return new ResponseEntity<>(new CategoryResponse("Comment Deleted !!", false), HttpStatus.OK);
	}
	
	
	@PutMapping("u-comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Integer commentId, 
			@RequestBody CommentDto dto){
	
		CommentDto ucDto = service.updateComment(dto, commentId);
		
		return new ResponseEntity<CommentDto>(ucDto, HttpStatus.OK);
		
	}
	
	@GetMapping("solo-comment/{comId}")
	public ResponseEntity<CommentDto> singleComment(@PathVariable Integer comId){
		 CommentDto commentDto = service.singleComment(comId);
		 return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
	}
	
	
}
