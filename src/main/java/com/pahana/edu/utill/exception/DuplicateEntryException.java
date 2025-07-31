package com.pahana.edu.utill.exception;

public class DuplicateEntryException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String redirectPath;
	private final String buttonLabel;

	public DuplicateEntryException(String message, String redirectPath, String buttonLabel) {
		super(message);
		this.redirectPath = redirectPath;
		this.buttonLabel = buttonLabel;
	}

	public String getRedirectPath() {
		return redirectPath;
	}

	public String getButtonLabel() {
		return buttonLabel;
	}

}
