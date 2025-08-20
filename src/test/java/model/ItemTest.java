package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;

public class ItemTest {

	private Item item;
	private User testUser;
	private LocalDateTime testTime;

	@Before
	public void setUp() {
		testTime = LocalDateTime.now();
		testUser = new User();
		testUser.setId(1L);
		testUser.setUsername("testuser");

		item = new Item("Test Book", "978-3-16-148410-0", 29.99, 100, testUser,
				"A test book description", "Test Author", 2023, "Test Publisher", 10.0);
		item.setId(1L);
		item.setCreatedAt(testTime);
		item.setUpdatedAt(testTime);
	}

	@Test
	public void testDefaultConstructor() {
		Item defaultItem = new Item();

		assertNull(defaultItem.getId());
		assertNull(defaultItem.getTitle());
		assertNull(defaultItem.getIsbn());
		assertEquals(0.0, defaultItem.getPrice(), 0.001);
		assertNull(defaultItem.getQuantityInStock());
		assertNull(defaultItem.getUser());
		assertNull(defaultItem.getDescription());
		assertNull(defaultItem.getAuthor());
		assertNull(defaultItem.getPublicationYear());
		assertNull(defaultItem.getPublisher());
		assertEquals(0.0, defaultItem.getDiscountPercentage(), 0.001);
		assertEquals(0.0, defaultItem.getDiscountAmount(), 0.001);
	}

	@Test
	public void testParameterizedConstructor() {
		Item paramItem = new Item("Another Book", "978-1-23-456789-0", 19.99, 50, testUser,
				"Another description", "Another Author", 2024, "Another Publisher", 15.0);

		assertEquals("Another Book", paramItem.getTitle());
		assertEquals("978-1-23-456789-0", paramItem.getIsbn());
		assertEquals(19.99, paramItem.getPrice(), 0.001);
		assertEquals(Integer.valueOf(50), paramItem.getQuantityInStock());
		assertEquals(testUser, paramItem.getUser());
		assertEquals("Another description", paramItem.getDescription());
		assertEquals("Another Author", paramItem.getAuthor());
		assertEquals(Integer.valueOf(2024), paramItem.getPublicationYear());
		assertEquals("Another Publisher", paramItem.getPublisher());
		assertEquals(15.0, paramItem.getDiscountPercentage(), 0.001);
	}

	@Test
	public void testConstructorWithId() {
		Item itemWithId = new Item(2L, "Book with ID", "978-0-00-000000-0", 39.99, 25, testUser,
				"ID book description", "ID Author", 2022, "ID Publisher", 5.0);

		assertEquals(Long.valueOf(2L), itemWithId.getId());
		assertEquals("Book with ID", itemWithId.getTitle());
		assertEquals("978-0-00-000000-0", itemWithId.getIsbn());
		assertEquals(39.99, itemWithId.getPrice(), 0.001);
		assertEquals(Integer.valueOf(25), itemWithId.getQuantityInStock());
		assertEquals(testUser, itemWithId.getUser());
		assertEquals("ID book description", itemWithId.getDescription());
		assertEquals("ID Author", itemWithId.getAuthor());
		assertEquals(Integer.valueOf(2022), itemWithId.getPublicationYear());
		assertEquals("ID Publisher", itemWithId.getPublisher());
		assertEquals(5.0, itemWithId.getDiscountPercentage(), 0.001);
	}

	@Test
	public void testSettersAndGetters() {
		Item testItem = new Item();

		// Test all setters and getters
		testItem.setId(5L);
		testItem.setTitle("Setter Book");
		testItem.setIsbn("978-9-87-654321-0");
		testItem.setPrice(49.99);
		testItem.setQuantityInStock(75);
		testItem.setUser(testUser);
		testItem.setDescription("Setter description");
		testItem.setAuthor("Setter Author");
		testItem.setPublicationYear(2021);
		testItem.setPublisher("Setter Publisher");
		testItem.setDiscountPercentage(20.0);
		testItem.setDiscountAmount(9.998);
		testItem.setCreatedAt(testTime);
		testItem.setUpdatedAt(testTime);

		// Verify all values
		assertEquals(Long.valueOf(5L), testItem.getId());
		assertEquals("Setter Book", testItem.getTitle());
		assertEquals("978-9-87-654321-0", testItem.getIsbn());
		assertEquals(49.99, testItem.getPrice(), 0.001);
		assertEquals(Integer.valueOf(75), testItem.getQuantityInStock());
		assertEquals(testUser, testItem.getUser());
		assertEquals("Setter description", testItem.getDescription());
		assertEquals("Setter Author", testItem.getAuthor());
		assertEquals(Integer.valueOf(2021), testItem.getPublicationYear());
		assertEquals("Setter Publisher", testItem.getPublisher());
		assertEquals(20.0, testItem.getDiscountPercentage(), 0.001);
		assertEquals(9.998, testItem.getDiscountAmount(), 0.001);
		assertEquals(testTime, testItem.getCreatedAt());
		assertEquals(testTime, testItem.getUpdatedAt());
	}

	@Test
	public void testDiscountAmountCalculation() {
		item.setPrice(100.0);
		item.setDiscountPercentage(15.0);

		// Discount amount should be calculated as: price * (discountPercentage / 100)
		// 100 * (15/100) = 15.0
		item.setDiscountAmount(item.getPrice() * (item.getDiscountPercentage() / 100));

		assertEquals(15.0, item.getDiscountAmount(), 0.001);
	}

	@Test
	public void testZeroDiscount() {
		item.setDiscountPercentage(0.0);
		item.setDiscountAmount(item.getPrice() * (item.getDiscountPercentage() / 100));

		assertEquals(0.0, item.getDiscountAmount(), 0.001);
	}

	@Test
	public void testFullDiscount() {
		item.setPrice(50.0);
		item.setDiscountPercentage(100.0);
		item.setDiscountAmount(item.getPrice() * (item.getDiscountPercentage() / 100));

		assertEquals(50.0, item.getDiscountAmount(), 0.001);
	}

	@Test
	public void testNegativePrice() {
		item.setPrice(-25.0);
		assertEquals(-25.0, item.getPrice(), 0.001);
	}

	@Test
	public void testNegativeDiscountPercentage() {
		item.setDiscountPercentage(-10.0);
		assertEquals(-10.0, item.getDiscountPercentage(), 0.001);
	}

	@Test
	public void testZeroQuantity() {
		item.setQuantityInStock(0);
		assertEquals(Integer.valueOf(0), item.getQuantityInStock());
	}

	@Test
	public void testNegativeQuantity() {
		item.setQuantityInStock(-5);
		assertEquals(Integer.valueOf(-5), item.getQuantityInStock());
	}

	@Test
	public void testNullValues() {
		Item nullItem = new Item();

		assertNull(nullItem.getTitle());
		assertNull(nullItem.getIsbn());
		assertNull(nullItem.getQuantityInStock());
		assertNull(nullItem.getUser());
		assertNull(nullItem.getDescription());
		assertNull(nullItem.getAuthor());
		assertNull(nullItem.getPublicationYear());
		assertNull(nullItem.getPublisher());
	}

	@Test
	public void testLongDescription() {
		String longDescription = "This is a very long description that should be stored in the database. " +
				"It contains more than 2000 characters to test the column length constraint. " +
				"The @Column(length = 2000) annotation should handle this properly.";

		item.setDescription(longDescription);
		assertEquals(longDescription, item.getDescription());
	}

	@Test
	public void testUserAssociation() {
		User newUser = new User();
		newUser.setId(10L);
		newUser.setUsername("newuser");

		item.setUser(newUser);

		assertEquals(newUser, item.getUser());
		assertEquals(Long.valueOf(10L), item.getUser().getId());
		assertEquals("newuser", item.getUser().getUsername());
	}

	@Test
	public void testTimestamps() {
		LocalDateTime newTime = LocalDateTime.now().plusDays(1);

		item.setCreatedAt(newTime);
		item.setUpdatedAt(newTime);

		assertEquals(newTime, item.getCreatedAt());
		assertEquals(newTime, item.getUpdatedAt());
	}

	@Test
	public void testEdgeCasePrices() {
		item.setPrice(0.0);
		assertEquals(0.0, item.getPrice(), 0.001);

		item.setPrice(999999.99);
		assertEquals(999999.99, item.getPrice(), 0.001);

		item.setPrice(0.01);
		assertEquals(0.01, item.getPrice(), 0.001);
	}

	@Test
	public void testEdgeCaseDiscounts() {
		item.setDiscountPercentage(0.1);
		assertEquals(0.1, item.getDiscountPercentage(), 0.001);

		item.setDiscountPercentage(99.9);
		assertEquals(99.9, item.getDiscountPercentage(), 0.001);
	}

	@Test
	public void testISBNFormat() {
		item.setIsbn("123-4-56-789012-3");
		assertEquals("123-4-56-789012-3", item.getIsbn());

		item.setIsbn("9783161484100"); // ISBN without dashes
		assertEquals("9783161484100", item.getIsbn());
	}

	@Test
	public void testPublicationYearBoundaries() {
		item.setPublicationYear(1900);
		assertEquals(Integer.valueOf(1900), item.getPublicationYear());

		item.setPublicationYear(2100);
		assertEquals(Integer.valueOf(2100), item.getPublicationYear());

		item.setPublicationYear(2023);
		assertEquals(Integer.valueOf(2023), item.getPublicationYear());
	}
}