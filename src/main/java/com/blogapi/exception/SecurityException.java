package com.blogapi.exception;

public class SecurityException extends RuntimeException {

	private String source;
	private String name;
	private String email;
	
	public SecurityException(String source, String name, String email) {
		super(String.format("%s not found with %s : %s", source, name, email));
		this.source = source;
		this.name = name;
		this.email = email;
	}

	/* this exception is when user name or password don't match*/

	public SecurityException() {
		super();

	}

	public SecurityException(String message) {
		super(message);

	}
	
	

	}
	
	
	
	
	

