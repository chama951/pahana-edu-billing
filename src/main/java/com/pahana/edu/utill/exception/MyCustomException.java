package com.pahana.edu.utill.exception;

public class MyCustomException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String redirectPath;

	public MyCustomException(String message, String redirectPath) {
		super(message);
		this.redirectPath = redirectPath;
	}

	public String getRedirectPath() {
		return redirectPath;
	}

}
