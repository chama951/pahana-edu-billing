package com.pahana.edu.utill.exception;

import java.util.Collections;
import java.util.List;

public class MyValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private final List<String> validationErrors;

	public MyValidationException(List<String> validationErrors) {
		super();
		this.validationErrors = validationErrors;
	}

	public MyValidationException(String singleError) {
		this(Collections.singletonList(singleError));
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

}
