package com.pahana.edu.utill;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.utill.exception.MyValidationException;

public class Validator {

	public static boolean validUser(User user) throws MyValidationException {
		List<String> errors = new ArrayList<>();

		if (user == null) {
			errors.add("User object cannot be null");
		} else {
			if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
				errors.add("Username cannot be empty");
			}
			if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
				errors.add("Username must be between 4-20 characters");
			}
			if (!user.getUsername().matches("^[a-zA-Z]+$")) {
				errors.add("Username can only contain letters");
			}
		}

		if (user.getRole() == null) {
			errors.add("User role must be specified");
		}

		if (user.getHashedPassword() == null || user.getHashedPassword().isEmpty()) {
			errors.add("Password cannot be empty");
		} else {
			if (user.getHashedPassword().length() < 8) {
				errors.add("Password must be at least 8 characters long");
			}

			if (!user.getHashedPassword().matches(".*[A-Z].*")) {
				errors.add("Password must contain at least one uppercase letter");
			}

			if (!user.getHashedPassword().matches(".*[a-z].*")) {
				errors.add("Password must contain at least one lowercase letter");
			}

			if (!user.getHashedPassword().matches(".*\\d.*")) {
				errors.add("Password must contain at least one number");
			}

			if (!user.getHashedPassword().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
				errors.add("Password must contain at least one special character");
			}

			if (user.getHashedPassword().matches(".*\\s.*")) {
				errors.add("Password cannot contain whitespace");
			}
		}

		if (!errors.isEmpty()) {
			throw new MyValidationException(
					errors);
		}
		return true;
	}

	private static final Pattern ISBN_PATTERN = Pattern
			.compile("^(?:ISBN(?:-13)?:? )?(97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

	public static boolean validItem(Item item) throws MyValidationException {
		List<String> errors = new ArrayList<>();

		if (item == null) {
			errors.add("Item cannot be null");
		} else {
			if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
				errors.add("Item title cannot be empty");
			} else if (item.getTitle().length() > 255) {
				errors.add("Item title cannot exceed 255 characters");
			}

			if (item.getIsbn() != null && !item.getIsbn().isBlank()) {
				if (!ISBN_PATTERN.matcher(item.getIsbn()).matches()) {
					errors.add("Invalid ISBN format");
				}
			}

			if (item.getPrice() < 0) {
				errors.add("Price cannot be negative");
			}

			if (item.getQuantityInStock() != null && item.getQuantityInStock() < 0) {
				errors.add("Quantity cannot be negative");
			}

			if (item.getDescription() != null && item.getDescription().length() > 2000) {
				errors.add("Description cannot exceed 2000 characters");
			}

			if (item.getAuthor() == null || item.getAuthor().trim().isEmpty()) {
				errors.add("Author cannot be empty");
			} else if (item.getAuthor().length() > 100) {
				errors.add("Author name cannot exceed 100 characters");
			}

			if (item.getPublicationYear() != null) {
				int currentYear = Year.now().getValue();
				if (item.getPublicationYear() < 1800 || item.getPublicationYear() > currentYear + 1) {
					errors.add(String.format("Publication year must be between 1800 and %d", currentYear + 1));
				}
			}

			if (item.getPublisher() != null && item.getPublisher().length() > 100) {
				errors.add("Publisher name cannot exceed 100 characters");
			}
		}

		if (!errors.isEmpty()) {
			throw new MyValidationException(errors);
		}

		return true;
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9\\s-]{6,20}$");

	public static boolean validCustomer(Customer customer) throws MyValidationException {
		List<String> errors = new ArrayList<>();

		if (customer == null) {
			errors.add("Customer cannot be null");
		} else {
			if (customer.getAccountNumber() != null) {
				if (customer.getAccountNumber() <= 0) {
					errors.add("Account number must be positive");
				}
				if (!String.valueOf(customer.getAccountNumber()).matches("^\\d+$")) {
					errors.add("Account number must contain only numbers");
				}
			}

			if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
				errors.add("First name cannot be empty");
			} else {
				if (customer.getFirstName().length() > 50) {
					errors.add("First name cannot exceed 50 characters");
				}
				if (!customer.getFirstName().matches("^[a-zA-Z]+$")) {
					errors.add("First name can only contain letters");
				}
			}

			if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
				errors.add("Last name cannot be empty");
			} else {
				if (customer.getLastName().length() > 50) {
					errors.add("Last name cannot exceed 50 characters");
				}
				if (!customer.getLastName().matches("^[a-zA-Z]+$")) {
					errors.add("Last name can only contain letters");
				}
			}

			if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
				errors.add("Address cannot be empty");
			} else if (customer.getAddress().length() > 200) {
				errors.add("Address cannot exceed 200 characters");
			}

			if (customer.getPhoneNumber() == null || customer.getPhoneNumber().trim().isEmpty()) {
				errors.add("Phone number cannot be empty");
			} else if (!PHONE_PATTERN.matcher(customer.getPhoneNumber()).matches()) {
				errors.add("Invalid phone number format");
			}

			if (customer.getEmail() != null && !customer.getEmail().isBlank()) {
				if (!EMAIL_PATTERN.matcher(customer.getEmail()).matches()) {
					errors.add("Invalid email format");
				}
				if (customer.getEmail().length() > 100) {
					errors.add("Email cannot exceed 100 characters");
				}
			}

			if (customer.getUnitsConsumed() != null && customer.getUnitsConsumed() < 0) {
				errors.add("Units consumed cannot be negative");
			}
		}

		if (!errors.isEmpty()) {
			throw new MyValidationException(errors);
		}

		return true;
	}

}
