package com.pahana.edu.utill.exception;

public class PahanaEduException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String redirectPath;

	public PahanaEduException(String message, String redirectPath) {
		super(message);
		this.redirectPath = redirectPath;
	}

	public String getRedirectPath() {
		return redirectPath;
	}

}
