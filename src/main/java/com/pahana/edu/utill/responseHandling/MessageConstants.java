package com.pahana.edu.utill.responseHandling;

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
	public static final String CUSTOMER_NUMBER_EXISTS = "Customer Number already exists. Please choose a different Customer Number";
	public static final String CUSTOMER_CREATED = "Customer created successfully";
	public static final String CUSTOMER_UPDATED = "Customer updated successfully";
	public static final String CUSTOMER_EMAIL_EXISTS = "Customer email already exists. Please choose a different email";
	public static final String CUSTOMER_PHONENO_EXISTS = "Customer phone number already exists. Please choose a different phone number";
	public static final String CUSTOMER_DELETED = "Customer deleted successfully";
	public static final String ALREADY_EXIST = " is already exist";
	public static final String ITEM_CREATED = "Item created successfully";
	public static final String ISBN_NUMBER_EXISTS = "ISBN already exists";
	public static final String ITEM_DELETED = "Item deleted successfully";
	public static final String ITEM_UPDATED = "Item update successfully";
	public static final String CUSTOMER_SELECTED = "Customer selected successfully";
	public static final String ITEM_ADDED_TO_THE_CART = "Item added to the cart successfully";
	public static final String QUANTITY_NOT_ENOUGH_IN_STOCK = "Quantity not enough in Stock";
	public static final String ITEM_REMOVED_CART = "Item removed from the cart";
	public static final String CART_CLEARED = "Cart Cleared";
	public static final String ERROR_REMOVING_ITEM = "Error removing item";
	public static final String ERROR_CLEAR_CART = "Error clearing cart";

	// User Validation Messeges

	// Prevent instantiation
	private MessageConstants() {
	}
}
