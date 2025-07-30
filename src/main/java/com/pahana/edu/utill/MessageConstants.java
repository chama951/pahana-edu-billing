package com.pahana.edu.utill;

public final class MessageConstants {

	// Authentication Messages
	public static final String LOGIN_SUCCESS = "Login successful! Welcome ";
	public static final String LOGIN_FAILED = "Invalid username or password";
	public static final String LOGOUT_SUCCESS = "You have been logged out successfully";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String INVALID_PASSWORD = "Invalid password";
	public static final String ACCOUNT_DEACTIVATED = "Your account has been deactivated. Please contact Admin for assistance";
	public static final String MUST_BE_LOGGED_IN = "You must be logged in to access this page";

	// User Management Messages
	public static final String USER_CREATED = "User created successfully";
	public static final String USER_UPDATED = "User updated successfully";
	public static final String USER_DELETED = "User deleted successfully";
	public static final String INCORRECT_CURRENT_PASSWORD = "Current password is incorrect";
	public static final String USERNAME_UPDATED = "Username updated successfully! Please log in again";
	public static final String PASSWORD_UPDATED = "Password updated successfully! Please log in again";
	public static final String USERNAME_EXISTS = "Username already exists. Please choose a different username";
	public static final String FIRST_USER_CREATED = "User created successfully!";
	public static final String CANNOT_DELETE_BY_SELF = "You cannot delete your own account. Please contact Admin for assistance";
	public static final String USER_UPDATE_BY_SELF = "You cannot update your own privileges or status. Please contact Admin for assistance";

	// Error Messages
	public static final String ERROR_GENERIC = "An error occurred. Please try again.";
	public static final String ERROR_REQUIRED_FIELD = "This field is required";

	// Validation Messages
	public static final String VALIDATION_EMAIL_INVALID = "Please enter a valid email address";
	public static final String VALIDATION_PASSWORD_COMPLEXITY = "Password must be at least 8 characters long";

	// Dashboard Messages
	public static final String WELCOME_MESSAGE = "Welcome to the Admin Dashboard";

	// Privilege Messages
	public static final String PRIVILEGE_INSUFFICIENT = "You do not have sufficient privileges to perform this action";

	// Customer Management Messeges
	public static final String CUSTOMER_NUMBER_EXISTS = "Customer Number already exists. Please choose a different Number";
	public static final String CUSTOMER_CREATED = "Customer created successfully";
	public static final String CUSTOMER_UPDATED = "Customer updated successfully";
	public static final String CUSTOMER_EMAIL_EXISTS = "Customer email already exists. Please choose a different Number";
	public static final String CUSTOMER_PHONENO_EXISTS = "Customer phone number already exists. Please choose a different Number";
	public static final String CUSTOMER_DELETED = "Customer deleted successfully";

	// Prevent instantiation
	private MessageConstants() {
	}
}
