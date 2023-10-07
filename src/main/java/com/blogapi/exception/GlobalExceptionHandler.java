package com.blogapi.exception;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapi.helper.ApiHelper;
import com.blogapi.helper.CategoryResponse;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
	@ExceptionHandler(CheckedException.class)
	public ResponseEntity<ApiHelper> exceptionHandler(CheckedException ex) {
		String message = ex.getMessage();
		ApiHelper helper = new ApiHelper(message, false);
		
		return new ResponseEntity<ApiHelper>(helper, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ApiHelper> sourceNotfound(SecurityException ex){
		String message = ex.getMessage();
		ApiHelper apiHelper = new ApiHelper(message, false);
		return new ResponseEntity<ApiHelper>(apiHelper, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiHelper> emailConstraint(DataIntegrityViolationException ex){
		String message = ex.getMostSpecificCause().getMessage();
		ApiHelper response = new ApiHelper(message, false);
		return new ResponseEntity<ApiHelper>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validationException(MethodArgumentNotValidException ex){
		Map<String, String> response = new HashMap<>();
		
		/*
		 * for (ObjectError err : ex.getBindingResult().getAllErrors())
		 *  { String field =  ((FieldError) err).getField(); 
		 *  String defaultMessage =err.getDefaultMessage();
		 * 
		 * response.put(field, defaultMessage); }
		 */
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
				String field = ((FieldError) error).getField();
				String message = error.getDefaultMessage();
				
				response.put(field, message);
		});
		
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	 public ResponseEntity<CategoryResponse>  invalidUrl(HttpRequestMethodNotSupportedException ex){
		
		String message = ex.getMessage();

		return new ResponseEntity<>(new CategoryResponse(message, false), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<CategoryResponse> nullException(NullPointerException ex){
		
		String message = ex.getMessage()+" value  found";

		return new ResponseEntity<CategoryResponse>(new CategoryResponse(message, false), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<CategoryResponse> fileNotFound(FileNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new CategoryResponse(message, false), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<CategoryResponse> jwtExpireExc(ExpiredJwtException ex){
		
		String message = ex.getMessage();
		
		return new ResponseEntity<CategoryResponse>(new CategoryResponse(message, false), HttpStatus.REQUEST_TIMEOUT);
		
	}
	
}
