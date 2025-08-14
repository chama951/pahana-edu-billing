package com.pahana.edu.utill;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.regex.Pattern;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;

public class Validator {

	/*
	 * try { Payment validatedPayment = Validator.validPayment(payment); // Proceed
	 * with validated payment } catch (IllegalArgumentException e) { // Handle
	 * validation error System.err.println("Payment validation failed: " +
	 * e.getMessage()); }
	 */

	public static User validUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User object cannot be null");
		}
		if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if (!user.getUsername().matches("^[a-zA-Z0-9._-]{4,20}$")) {
			throw new IllegalArgumentException(
					"Username must be 4-20 characters and can only contain letters, numbers, ., _ or -");
		}
		if (user.getRole() == null) {
			throw new IllegalArgumentException("User role must be specified");
		}
		if (user.getHashedPassword() == null ||
				!user.getHashedPassword().matches("^\\$2[aby]\\$\\d{2}\\$[./0-9A-Za-z]{53}$")) {
			throw new IllegalArgumentException("Invalid password hash format");
		}
		return user;
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

	public static BillItem validBillItem(BillItem billItem) {
		if (billItem == null) {
			throw new IllegalArgumentException("Bill item cannot be null");
		}
		if (billItem.getQuantity() == null || billItem.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be a positive number");
		}
		if (billItem.getUnitPrice() < 0) {
			throw new IllegalArgumentException("Unit price cannot be negative");
		}
		if (billItem.getSubTotal() < 0) {
			throw new IllegalArgumentException("Subtotal cannot be negative");
		}
		if (billItem.getDiscountAmount() < 0 || billItem.getDiscountAmount() > 100) {
			throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
		}
		if (billItem.getBill() == null) {
			throw new IllegalArgumentException("Bill item must be associated with a bill");
		}
		if (billItem.getItem() == null) {
			throw new IllegalArgumentException("Bill item must be associated with an item");
		}
		return billItem;
	}

	public static Bill validBill(Bill bill) {
		if (bill == null) {
			throw new IllegalArgumentException("Bill cannot be null");
		}
		if (bill.getBillDate() == null) {
			throw new IllegalArgumentException("Bill date cannot be null");
		}
		if (bill.getBillDate().isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Bill date cannot be in the future");
		}
		validatePositiveAmount(bill.getTotalAmount(), "Total amount");
		validateNonNegativeAmount(bill.getDiscountAmount(), "Discount amount");
		if (bill.getDiscountAmount().compareTo(bill.getTotalAmount()) > 0) {
			throw new IllegalArgumentException("Discount cannot be greater than total amount");
		}
		if (bill.getCustomer() == null) {
			throw new IllegalArgumentException("Bill must be associated with a customer");
		}
		if (bill.getBillItems() == null || bill.getBillItems().isEmpty()) {
			throw new IllegalArgumentException("Bill must contain at least one item");
		}
		return bill;
	}

	private static void validatePositiveAmount(BigDecimal amount, String fieldName) {
		if (amount == null) {
			throw new IllegalArgumentException(fieldName + " cannot be null");
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException(fieldName + " must be positive");
		}
		validateAmountPrecision(amount, fieldName);
	}

	private static void validateNonNegativeAmount(BigDecimal amount, String fieldName) {
		if (amount == null) {
			throw new IllegalArgumentException(fieldName + " cannot be null");
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException(fieldName + " cannot be negative");
		}
		validateAmountPrecision(amount, fieldName);
	}

	private static void validateAmountPrecision(BigDecimal amount, String fieldName) {
		if (amount.scale() > 2) {
			throw new IllegalArgumentException(fieldName + " cannot have more than 2 decimal places");
		}
	}

}
