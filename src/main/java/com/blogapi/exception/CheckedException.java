package com.blogapi.exception;

import lombok.Data;

@Data
public class CheckedException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sourceName;
	private String fieldName;
	private Integer fieldValue;
	
	public CheckedException(String sourceName, String fieldName, Integer fieldValue) {
		super(String.format("%s not found with %s : %s", sourceName, fieldName, fieldValue));
		this.sourceName = sourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
	
	
}
