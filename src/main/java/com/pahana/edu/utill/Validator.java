package com.pahana.edu.utill;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.utill.exception.MyValidationException;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class Validator {

	public static boolean validUser(User user) throws MyValidationException {
		List<String> errors = new ArrayList<>();

		if (user == null) {
			errors.add("User object cannot be null");
		}
		if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
			errors.add("Username cannot be empty");
		}
		if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
			errors.add("Username must be between 4-20 characters");
		}
		if (!user.getUsername().matches("^[a-zA-Z]+$")) {
			errors.add("Username can only contain letters");
		}

		if (user.getRole() == null) {
			errors.add("User role must be specified");
		}

		if (user.getHashedPassword() == null || user.getHashedPassword().isEmpty()) {
			errors.add("Password cannot be empty");
		}
		// Minimum 8 characters
		if (user.getHashedPassword().length() < 8) {
			errors.add("Password must be at least 8 characters long");
		}

		// At least one uppercase letter
		if (!user.getHashedPassword().matches(".*[A-Z].*")) {
			errors.add("Password must contain at least one uppercase letter");
		}

		// At least one lowercase letter
		if (!user.getHashedPassword().matches(".*[a-z].*")) {
			errors.add("Password must contain at least one lowercase letter");
		}

		// At least one digit
		if (!user.getHashedPassword().matches(".*\\d.*")) {
			errors.add("Password must contain at least one number");
		}

		// At least one special character
		if (!user.getHashedPassword().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			errors.add("Password must contain at least one special character");
		}

		// No whitespace
		if (user.getHashedPassword().matches(".*\\s.*")) {
			errors.add("Password cannot contain whitespace");
		}

		if (!errors.isEmpty()) {
			throw new MyValidationException(
					errors);
		}
		return true;
	}

	// ISBN validation patterns (supports ISBN-10 and ISBN-13)
	private static final Pattern ISBN_PATTERN = Pattern.compile(
			"^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

	public static Item validItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
			throw new IllegalArgumentException("Item title cannot be empty");
		}
		if (item.getTitle().length() > 255) {
			throw new IllegalArgumentException("Item title cannot exceed 255 characters");
		}
		if (item.getIsbn() != null && !item.getIsbn().isBlank()) {
			if (!ISBN_PATTERN.matcher(item.getIsbn()).matches()) {
				throw new IllegalArgumentException("Invalid ISBN format");
			}
		}
		if (item.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		if (item.getQuantityInStock() != null && item.getQuantityInStock() < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}
		if (item.getDescription() != null && item.getDescription().length() > 2000) {
			throw new IllegalArgumentException("Description cannot exceed 2000 characters");
		}
		if (item.getAuthor() == null || item.getAuthor().trim().isEmpty()) {
			throw new IllegalArgumentException("Author cannot be empty");
		}
		if (item.getAuthor().length() > 100) {
			throw new IllegalArgumentException("Author name cannot exceed 100 characters");
		}
		if (item.getPublicationYear() != null) {
			int currentYear = Year.now().getValue();
			if (item.getPublicationYear() < 1800 || item.getPublicationYear() > currentYear + 1) {
				throw new IllegalArgumentException(
						String.format("Publication year must be between 1800 and %d", currentYear + 1));
			}
		}
		if (item.getPublisher() != null && item.getPublisher().length() > 100) {
			throw new IllegalArgumentException("Publisher name cannot exceed 100 characters");
		}
		return item;
	}

	// Email validation pattern
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	// Phone number validation pattern (basic international format)
	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9\\s-]{6,20}$");

	public static Customer validCustomer(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
		if (customer.getAccountNumber() != null && customer.getAccountNumber() <= 0) {
			throw new IllegalArgumentException("Account number must be positive");
		}
		if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
			throw new IllegalArgumentException("First name cannot be empty");
		}
		if (customer.getFirstName().length() > 50) {
			throw new IllegalArgumentException("First name cannot exceed 50 characters");
		}
		if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be empty");
		}
		if (customer.getLastName().length() > 50) {
			throw new IllegalArgumentException("Last name cannot exceed 50 characters");
		}
		if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
			throw new IllegalArgumentException("Address cannot be empty");
		}
		if (customer.getAddress().length() > 200) {
			throw new IllegalArgumentException("Address cannot exceed 200 characters");
		}
		if (customer.getPhoneNumber() == null || customer.getPhoneNumber().trim().isEmpty()) {
			throw new IllegalArgumentException("Phone number cannot be empty");
		}
		if (!PHONE_PATTERN.matcher(customer.getPhoneNumber()).matches()) {
			throw new IllegalArgumentException("Invalid phone number format");
		}
		if (customer.getEmail() != null && !customer.getEmail().isBlank()) {
			if (!EMAIL_PATTERN.matcher(customer.getEmail()).matches()) {
				throw new IllegalArgumentException("Invalid email format");
			}
			if (customer.getEmail().length() > 100) {
				throw new IllegalArgumentException("Email cannot exceed 100 characters");
			}
		}
		if (customer.getUnitsConsumed() != null && customer.getUnitsConsumed() < 0) {
			throw new IllegalArgumentException("Units consumed cannot be negative");
		}
		return customer;
	}

}
