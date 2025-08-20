package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.model.enums.UserRole;

public class BillTest {

	private Bill bill;
	private Customer customer;
	private User user;
	private List<BillItem> billItems;
	private LocalDateTime testTime;

	@Before
	public void setUp() {
		testTime = LocalDateTime.now();

		// Create customer
		customer = new Customer();
		customer.setId(1L);
		customer.setAccountNumber(12345L);
		customer.setFirstName("John");
		customer.setLastName("Doe");

		// Create user
		user = new User("testuser", "hashedpassword123", UserRole.ADMIN, true, testTime);
		user.setId(1L);

		// Create bill items
		billItems = new ArrayList<>();

		Item item1 = new Item();
		item1.setId(1L);
		item1.setTitle("Test Item 1");
		item1.setPrice(25.0);

		Item item2 = new Item();
		item2.setId(2L);
		item2.setTitle("Test Item 2");
		item2.setPrice(15.0);

		BillItem billItem1 = new BillItem();
		billItem1.setId(1L);
		billItem1.setQuantity(2);
		billItem1.setUnitPrice(25.0);
		billItem1.setSubTotal(50.0);
		billItem1.setDiscountAmount(5.0);
		billItem1.setItem(item1);

		BillItem billItem2 = new BillItem();
		billItem2.setId(2L);
		billItem2.setQuantity(3);
		billItem2.setUnitPrice(15.0);
		billItem2.setSubTotal(45.0);
		billItem2.setDiscountAmount(4.5);
		billItem2.setItem(item2);

		billItems.add(billItem1);
		billItems.add(billItem2);

		// Create bill
		bill = new Bill();
		bill.setId(1L);
		bill.setTotalAmount(95.0);
		bill.setDiscountAmount(9.5);
		bill.setNetAmount(85.5);
		bill.setBillStatus(BillStatus.PENDING);
		bill.setCreatedAt(testTime);
		bill.setCustomer(customer);
		bill.setUser(user);
		bill.setBillItems(billItems);

		// Set bidirectional relationships
		for (BillItem billItem : billItems) {
			billItem.setBill(bill);
		}
	}

	@Test
	public void testDefaultConstructor() {
		Bill defaultBill = new Bill();

		assertNull(defaultBill.getId());
		assertNull(defaultBill.getTotalAmount());
		assertNull(defaultBill.getDiscountAmount());
		assertNull(defaultBill.getNetAmount());
		assertNull(defaultBill.getBillStatus());
		assertNull(defaultBill.getCreatedAt());
		assertNull(defaultBill.getCustomer());
		assertNull(defaultBill.getUser());
		assertNull(defaultBill.getBillItems());
	}

	@Test
	public void testSettersAndGetters() {
		Bill testBill = new Bill();

		testBill.setId(2L);
		testBill.setTotalAmount(100.0);
		testBill.setDiscountAmount(10.0);
		testBill.setNetAmount(90.0);
		testBill.setBillStatus(BillStatus.PAID);
		testBill.setCreatedAt(testTime);
		testBill.setCustomer(customer);
		testBill.setUser(user);
		testBill.setBillItems(billItems);

		assertEquals(Long.valueOf(2L), testBill.getId());
		assertEquals(100.0, testBill.getTotalAmount(), 0.001);
		assertEquals(10.0, testBill.getDiscountAmount(), 0.001);
		assertEquals(90.0, testBill.getNetAmount(), 0.001);
		assertEquals(BillStatus.PAID, testBill.getBillStatus());
		assertEquals(testTime, testBill.getCreatedAt());
		assertEquals(customer, testBill.getCustomer());
		assertEquals(user, testBill.getUser());
		assertEquals(billItems, testBill.getBillItems());
	}

	@Test
	public void testNetAmountCalculation() {
		// Test that net amount is calculated correctly
		assertEquals(bill.getNetAmount(),
				bill.getTotalAmount() - bill.getDiscountAmount(),
				0.001);
	}

	@Test
	public void testCustomerAssociation() {
		assertEquals(customer, bill.getCustomer());
		assertEquals(Long.valueOf(1L), bill.getCustomer().getId());
		assertEquals("John", bill.getCustomer().getFirstName());
		assertEquals("Doe", bill.getCustomer().getLastName());
	}

	@Test
	public void testUserAssociation() {
		assertEquals(user, bill.getUser());
		assertEquals(Long.valueOf(1L), bill.getUser().getId());
		assertEquals("testuser", bill.getUser().getUsername());
		assertEquals(UserRole.ADMIN, bill.getUser().getRole());
		assertTrue(bill.getUser().getIsActive());
	}

	@Test
	public void testBillItemsAssociation() {
		List<BillItem> items = bill.getBillItems();

		assertNotNull(items);
		assertEquals(2, items.size());

		BillItem firstItem = items.get(0);
		assertEquals(Long.valueOf(1L), firstItem.getId());
		assertEquals(Integer.valueOf(2), firstItem.getQuantity());
		assertEquals(25.0, firstItem.getUnitPrice(), 0.001);
		assertEquals(50.0, firstItem.getSubTotal(), 0.001);
		assertEquals(5.0, firstItem.getDiscountAmount(), 0.001);
		assertEquals(bill, firstItem.getBill());

		BillItem secondItem = items.get(1);
		assertEquals(Long.valueOf(2L), secondItem.getId());
		assertEquals(Integer.valueOf(3), secondItem.getQuantity());
		assertEquals(15.0, secondItem.getUnitPrice(), 0.001);
		assertEquals(45.0, secondItem.getSubTotal(), 0.001);
		assertEquals(4.5, secondItem.getDiscountAmount(), 0.001);
	}

	@Test
	public void testBillStatusValues() {
		bill.setBillStatus(BillStatus.DRAFT);
		assertEquals(BillStatus.DRAFT, bill.getBillStatus());
		assertEquals("Draft", bill.getBillStatus().getDisplayName());

		bill.setBillStatus(BillStatus.PENDING);
		assertEquals(BillStatus.PENDING, bill.getBillStatus());
		assertEquals("Pending", bill.getBillStatus().getDisplayName());

		bill.setBillStatus(BillStatus.PAID);
		assertEquals(BillStatus.PAID, bill.getBillStatus());
		assertEquals("Paid", bill.getBillStatus().getDisplayName());

		bill.setBillStatus(BillStatus.CANCELLED);
		assertEquals(BillStatus.CANCELLED, bill.getBillStatus());
		assertEquals("Cancelled", bill.getBillStatus().getDisplayName());

		bill.setBillStatus(BillStatus.REFUNDED);
		assertEquals(BillStatus.REFUNDED, bill.getBillStatus());
		assertEquals("Refunded", bill.getBillStatus().getDisplayName());
	}

	@Test
	public void testTotalAmountFromBillItems() {
		double calculatedTotal = bill.getBillItems().stream()
				.mapToDouble(BillItem::getSubTotal)
				.sum();

		double calculatedDiscount = bill.getBillItems().stream()
				.mapToDouble(BillItem::getDiscountAmount)
				.sum();

		assertEquals(bill.getTotalAmount(), calculatedTotal, 0.001);
		assertEquals(bill.getDiscountAmount(), calculatedDiscount, 0.001);
	}

	@Test
	public void testBillWithNoItems() {
		Bill emptyBill = new Bill();
		emptyBill.setId(3L);
		emptyBill.setTotalAmount(0.0);
		emptyBill.setDiscountAmount(0.0);
		emptyBill.setNetAmount(0.0);
		emptyBill.setBillStatus(BillStatus.DRAFT);
		emptyBill.setCustomer(customer);
		emptyBill.setUser(user);
		emptyBill.setBillItems(new ArrayList<>());

		assertEquals(0.0, emptyBill.getTotalAmount(), 0.001);
		assertEquals(0.0, emptyBill.getNetAmount(), 0.001);
		assertTrue(emptyBill.getBillItems().isEmpty());
	}

	@Test
	public void testBillWithNullItems() {
		Bill nullItemsBill = new Bill();
		nullItemsBill.setId(4L);
		nullItemsBill.setBillItems(null);

		assertNull(nullItemsBill.getBillItems());
	}

	@Test
	public void testTimestampBehavior() {
		assertNotNull(bill.getCreatedAt());
		assertTrue(bill.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));

		// Test that timestamp can be overridden
		LocalDateTime customTime = testTime.minusDays(1);
		bill.setCreatedAt(customTime);
		assertEquals(customTime, bill.getCreatedAt());
	}

	@Test
	public void testBidirectionalRelationships() {
		// Test customer-bill relationship
//		assertEquals(bill, bill.getCustomer().getBillList().get(0));

		// Test user-bill relationship
		assertEquals(user, bill.getUser());

		// Test bill-billItems relationship
		for (BillItem billItem : bill.getBillItems()) {
			assertEquals(bill, billItem.getBill());
		}
	}

	@Test
	public void testFinancialEdgeCases() {
		// Test zero values
		bill.setTotalAmount(0.0);
		bill.setDiscountAmount(0.0);
		bill.setNetAmount(0.0);

		assertEquals(0.0, bill.getTotalAmount(), 0.001);
		assertEquals(0.0, bill.getNetAmount(), 0.001);

		// Test negative values
		bill.setTotalAmount(-50.0);
		bill.setDiscountAmount(-10.0);
		bill.setNetAmount(-40.0);

		assertEquals(-50.0, bill.getTotalAmount(), 0.001);
		assertEquals(-40.0, bill.getNetAmount(), 0.001);

		// Test large values
		bill.setTotalAmount(999999.99);
		bill.setDiscountAmount(50000.0);
		bill.setNetAmount(949999.99);

		assertEquals(999999.99, bill.getTotalAmount(), 0.001);
		assertEquals(949999.99, bill.getNetAmount(), 0.001);
	}

	@Test
	public void testDiscountLargerThanTotal() {
		bill.setTotalAmount(100.0);
		bill.setDiscountAmount(150.0);
		bill.setNetAmount(-50.0); // Negative net amount

		assertEquals(-50.0, bill.getNetAmount(), 0.001);
	}

	@Test
	public void testBillStatusWorkflow() {
		// Simulate typical bill workflow
		bill.setBillStatus(BillStatus.DRAFT);
		assertEquals(BillStatus.DRAFT, bill.getBillStatus());

		bill.setBillStatus(BillStatus.PENDING);
		assertEquals(BillStatus.PENDING, bill.getBillStatus());

		bill.setBillStatus(BillStatus.PAID);
		assertEquals(BillStatus.PAID, bill.getBillStatus());

		// Test refund scenario
		bill.setBillStatus(BillStatus.REFUNDED);
		assertEquals(BillStatus.REFUNDED, bill.getBillStatus());
	}

	@Test
	public void testUserRoleInBillContext() {
		// Test different user roles that might create bills
		User adminUser = new User(UserRole.ADMIN, true);
		User cashierUser = new User(UserRole.CASHIER, true);
		User managerUser = new User(UserRole.INVENTORY_MANAGER, true);

		bill.setUser(adminUser);
		assertEquals(UserRole.ADMIN, bill.getUser().getRole());

		bill.setUser(cashierUser);
		assertEquals(UserRole.CASHIER, bill.getUser().getRole());

		bill.setUser(managerUser);
		assertEquals(UserRole.INVENTORY_MANAGER, bill.getUser().getRole());
	}

	@Test
	public void testCustomerBillHistoryThroughBill() {
		// Test that customer's bill list contains this bill
//		assertTrue(bill.getCustomer().getBillList().contains(bill));

		// Test accessing customer information through bill
		assertEquals("John", bill.getCustomer().getFirstName());
		assertEquals("Doe", bill.getCustomer().getLastName());
		assertEquals(Long.valueOf(12345L), bill.getCustomer().getAccountNumber());
	}

	@Test
	public void testBillItemDetailsThroughBill() {
		BillItem firstItem = bill.getBillItems().get(0);

		// Test item details through bill item
		assertNotNull(firstItem.getItem());
		assertEquals("Test Item 1", firstItem.getItem().getTitle());
		assertEquals(25.0, firstItem.getItem().getPrice(), 0.001);

		// Test bill item calculations
		assertEquals(firstItem.getSubTotal(),
				firstItem.getQuantity() * firstItem.getUnitPrice(),
				0.001);
	}

	@Test
	public void testBillAmountConsistency() {
		// Verify that amounts are consistent
		assertTrue(bill.getNetAmount() <= bill.getTotalAmount());
		assertTrue(bill.getDiscountAmount() <= bill.getTotalAmount());
		assertEquals(bill.getTotalAmount() - bill.getDiscountAmount(),
				bill.getNetAmount(), 0.001);
	}
}
