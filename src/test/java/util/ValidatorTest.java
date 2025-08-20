package util;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.Validator;
import com.pahana.edu.utill.exception.MyValidationException;

public class ValidatorTest {

	private User validUser;
	private Item validItem;
	private Customer validCustomer;

	@Before
	public void setUp() {
		// Create valid user
		validUser = new User("testuser", "Password123!", UserRole.ADMIN, true, LocalDateTime.now());
		validUser.setId(1L);

		// Create valid item
		validItem = new Item();
		validItem.setId(1L);
		validItem.setTitle("Test Book");
		validItem.setIsbn("978-3-16-148410-0");
		validItem.setPrice(29.99);
		validItem.setQuantityInStock(100);
		validItem.setDescription("A test book description");
		validItem.setAuthor("Test Author");
		validItem.setPublicationYear(2023);
		validItem.setPublisher("Test Publisher");

		// Create valid customer
		validCustomer = new Customer();
		validCustomer.setId(1L);
		validCustomer.setAccountNumber(12345L);
		validCustomer.setFirstName("John");
		validCustomer.setLastName("Doe");
		validCustomer.setAddress("123 Main St, City, State");
		validCustomer.setPhoneNumber("+1-555-0123");
		validCustomer.setEmail("john.doe@example.com");
		validCustomer.setUnitsConsumed(250);
	}

	// User Validation Tests
	@Test
	public void testValidUser() throws MyValidationException {
		assertTrue(Validator.validUser(validUser));
	}

	@Test(expected = MyValidationException.class)
	public void testUserEmptyUsername() throws MyValidationException {
		User user = new User("", "Password123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserShortUsername() throws MyValidationException {
		User user = new User("abc", "Password123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserLongUsername() throws MyValidationException {
		User user = new User("verylongusernameexceedingtwentychars", "Password123!", UserRole.ADMIN, true,
				LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserUsernameWithNumbers() throws MyValidationException {
		User user = new User("user123", "Password123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserNullRole() throws MyValidationException {
		User user = new User("testuser", "Password123!", null, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserEmptyPassword() throws MyValidationException {
		User user = new User("testuser", "", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserShortPassword() throws MyValidationException {
		User user = new User("testuser", "Short1!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserPasswordNoUppercase() throws MyValidationException {
		User user = new User("testuser", "password123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserPasswordNoLowercase() throws MyValidationException {
		User user = new User("testuser", "PASSWORD123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserPasswordNoNumber() throws MyValidationException {
		User user = new User("testuser", "Password!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserPasswordNoSpecialChar() throws MyValidationException {
		User user = new User("testuser", "Password123", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	@Test(expected = MyValidationException.class)
	public void testUserPasswordWithWhitespace() throws MyValidationException {
		User user = new User("testuser", "Password 123!", UserRole.ADMIN, true, LocalDateTime.now());
		Validator.validUser(user);
	}

	// Item Validation Tests
	@Test
	public void testValidItem() throws MyValidationException {
		assertTrue(Validator.validItem(validItem));
	}

	@Test(expected = MyValidationException.class)
	public void testItemNull() throws MyValidationException {
		Validator.validItem(null);
	}

	@Test(expected = MyValidationException.class)
	public void testItemEmptyTitle() throws MyValidationException {
		Item item = new Item();
		item.setTitle("");
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemLongTitle() throws MyValidationException {
		Item item = new Item();
		item.setTitle("A".repeat(256));
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemInvalidISBN() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setIsbn("invalid-isbn");
		Validator.validItem(item);
	}

	@Test
	public void testItemValidISBNFormats() throws MyValidationException {
		String[] validISBNs = {
				"978-3-16-148410-0",
				"9783161484100",
				"978-0-306-40615-7",
				"9780306406157",
				"978-1-4028-9462-6"
		};

		for (String isbn : validISBNs) {
			Item item = new Item();
			item.setTitle("Test Book");
			item.setIsbn(isbn);
			item.setAuthor("Author");
			item.setPrice(10.0);
			assertTrue(Validator.validItem(item));
		}
	}

	@Test(expected = MyValidationException.class)
	public void testItemNegativePrice() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(-10.0);
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemNegativeQuantity() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(10.0);
		item.setQuantityInStock(-5);
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemLongDescription() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(10.0);
		item.setDescription("A".repeat(2001));
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemEmptyAuthor() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("");
		item.setPrice(10.0);
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemLongAuthor() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("A".repeat(101));
		item.setPrice(10.0);
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemInvalidPublicationYear() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(10.0);
		item.setPublicationYear(1799); // Before 1800
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemFuturePublicationYear() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(10.0);
		item.setPublicationYear(Year.now().getValue() + 2); // More than current year + 1
		Validator.validItem(item);
	}

	@Test(expected = MyValidationException.class)
	public void testItemLongPublisher() throws MyValidationException {
		Item item = new Item();
		item.setTitle("Test Book");
		item.setAuthor("Author");
		item.setPrice(10.0);
		item.setPublisher("A".repeat(101));
		Validator.validItem(item);
	}

	// Customer Validation Tests
	@Test
	public void testValidCustomer() throws MyValidationException {
		assertTrue(Validator.validCustomer(validCustomer));
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerNull() throws MyValidationException {
		Validator.validCustomer(null);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerNegativeAccountNumber() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(-123L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerEmptyFirstName() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerLongFirstName() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("A".repeat(51));
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerFirstNameWithNumbers() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John123");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerEmptyLastName() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerEmptyAddress() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("");
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerLongAddress() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("A".repeat(201));
		customer.setPhoneNumber("555-0123");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerEmptyPhoneNumber() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerInvalidPhoneNumber() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("invalid-phone");
		Validator.validCustomer(customer);
	}

	@Test
	public void testCustomerValidPhoneFormats() throws MyValidationException {
		String[] validPhones = {
				"555-0123",
				"5550123",
				"+1-555-0123",
				"+15550123",
				"555 0123",
				"+1 555 0123"
		};

		for (String phone : validPhones) {
			Customer customer = new Customer();
			customer.setAccountNumber(12345L);
			customer.setFirstName("John");
			customer.setLastName("Doe");
			customer.setAddress("123 Main St");
			customer.setPhoneNumber(phone);
			assertTrue(Validator.validCustomer(customer));
		}
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerInvalidEmail() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setEmail("invalid-email");
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerLongEmail() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setEmail("a".repeat(95) + "@example.com"); // > 100 chars
		Validator.validCustomer(customer);
	}

	@Test(expected = MyValidationException.class)
	public void testCustomerNegativeUnits() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setUnitsConsumed(-5);
		Validator.validCustomer(customer);
	}

	@Test
	public void testCustomerNullUnits() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setUnitsConsumed(null); // Should be allowed
		assertTrue(Validator.validCustomer(customer));
	}

	@Test
	public void testCustomerNullEmail() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setEmail(null); // Should be allowed
		assertTrue(Validator.validCustomer(customer));
	}

	@Test
	public void testCustomerBlankEmail() throws MyValidationException {
		Customer customer = new Customer();
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setAddress("123 Main St");
		customer.setPhoneNumber("555-0123");
		customer.setEmail("   "); // Should be allowed (treated as blank)
		assertTrue(Validator.validCustomer(customer));
	}

	// Multiple Error Tests
	@Test
	public void testMultipleValidationErrors() {
		User invalidUser = new User("us", "pass", null, true, LocalDateTime.now());

		try {
			Validator.validUser(invalidUser);
			fail("Expected MyValidationException");
		} catch (MyValidationException e) {
			List<String> errors = e.getValidationErrors();
			assertTrue(errors.contains("Username must be between 4-20 characters"));
			assertTrue(errors.contains("Password must be at least 8 characters long"));
			assertTrue(errors.contains("Password must contain at least one uppercase letter"));
			assertTrue(errors.contains("Password must contain at least one number"));
			assertTrue(errors.contains("Password must contain at least one special character"));
			assertTrue(errors.contains("User role must be specified"));
			assertTrue(errors.size() >= 6);
		}
	}

	@Test
	public void testErrorMessagesAreDescriptive() {
		try {
			User user = new User("user123", "password", UserRole.ADMIN, true, LocalDateTime.now());
			Validator.validUser(user);
			fail("Expected MyValidationException");
		} catch (MyValidationException e) {
			List<String> errors = e.getValidationErrors();
			assertTrue(errors.stream().anyMatch(msg -> msg.contains("Username can only contain letters")));
			assertTrue(errors.stream().anyMatch(msg -> msg.contains("uppercase")));
			assertTrue(errors.stream().anyMatch(msg -> msg.contains("number")));
			assertTrue(errors.stream().anyMatch(msg -> msg.contains("special character")));
		}
	}
}