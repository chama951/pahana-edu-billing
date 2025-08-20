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

public class CustomerTest {

	private Customer customer;
	private LocalDateTime testTime;
	private List<Bill> testBills;
	private List<BillItem> testBillItems;
	private User testUser;
	private Item testItem1, testItem2;

	@Before
	public void setUp() {
		testTime = LocalDateTime.now();
		testBills = new ArrayList<>();
		testBillItems = new ArrayList<>();

		// Create test user
		testUser = new User();
		testUser.setId(1L);
		testUser.setUsername("testuser");

		// Create test items
		testItem1 = new Item();
		testItem1.setId(1L);
		testItem1.setTitle("Test Item 1");
		testItem1.setPrice(25.0);

		testItem2 = new Item();
		testItem2.setId(2L);
		testItem2.setTitle("Test Item 2");
		testItem2.setPrice(15.0);

		// Create test bill items
		BillItem billItem1 = new BillItem();
		billItem1.setId(1L);
		billItem1.setQuantity(2);
		billItem1.setUnitPrice(25.0);
		billItem1.setSubTotal(50.0); // 2 * 25
		billItem1.setDiscountAmount(5.0);
		billItem1.setItem(testItem1);

		BillItem billItem2 = new BillItem();
		billItem2.setId(2L);
		billItem2.setQuantity(3);
		billItem2.setUnitPrice(15.0);
		billItem2.setSubTotal(45.0); // 3 * 15
		billItem2.setDiscountAmount(4.5);
		billItem2.setItem(testItem2);

		testBillItems.add(billItem1);
		testBillItems.add(billItem2);

		// Create test bills with bill items
		Bill bill1 = new Bill();
		bill1.setId(1L);
		bill1.setTotalAmount(95.0); // 50 + 45
		bill1.setDiscountAmount(9.5); // 5 + 4.5
		bill1.setNetAmount(85.5); // 95 - 9.5
		bill1.setBillStatus(BillStatus.PAID);
		bill1.setCreatedAt(testTime);
		bill1.setUser(testUser);
		bill1.setBillItems(testBillItems);

		Bill bill2 = new Bill();
		bill2.setId(2L);
		bill2.setTotalAmount(150.0);
		bill2.setDiscountAmount(15.0);
		bill2.setNetAmount(135.0);
		bill2.setBillStatus(BillStatus.PENDING);
		bill2.setCreatedAt(testTime.plusDays(1));
		bill2.setUser(testUser);

		Bill bill3 = new Bill();
		bill3.setId(3L);
		bill3.setTotalAmount(75.0);
		bill3.setDiscountAmount(0.0);
		bill3.setNetAmount(75.0);
		bill3.setBillStatus(BillStatus.DRAFT);
		bill3.setCreatedAt(testTime.plusDays(2));
		bill3.setUser(testUser);

		testBills.add(bill1);
		testBills.add(bill2);
		testBills.add(bill3);

		// Create customer
		customer = new Customer(12345L, "John", "Doe", "123 Main St", "555-0123",
				"john.doe@email.com", 250);
		customer.setId(1L);
		customer.setCreatedAt(testTime);
		customer.setUpdatedAt(testTime);
		customer.setBillList(testBills);

		// Set customer reference in bills (bidirectional relationship)
		for (Bill bill : testBills) {
			bill.setCustomer(customer);
		}

		// Set bill reference in bill items (bidirectional relationship)
		for (BillItem billItem : testBillItems) {
			billItem.setBill(bill1); // Associate with first bill
		}
	}

	@Test
	public void testDefaultConstructor() {
		Customer defaultCustomer = new Customer();

		assertNull(defaultCustomer.getId());
		assertNull(defaultCustomer.getAccountNumber());
		assertNull(defaultCustomer.getFirstName());
		assertNull(defaultCustomer.getLastName());
		assertNull(defaultCustomer.getAddress());
		assertNull(defaultCustomer.getPhoneNumber());
		assertNull(defaultCustomer.getEmail());
		assertNull(defaultCustomer.getUnitsConsumed());
		assertNull(defaultCustomer.getCreatedAt());
		assertNull(defaultCustomer.getUpdatedAt());
		assertNotNull(defaultCustomer.getBillList());
		assertTrue(defaultCustomer.getBillList().isEmpty());
	}

	@Test
	public void testBillAssociation() {
		assertEquals(3, customer.getBillList().size());

		Bill firstBill = customer.getBillList().get(0);
		assertEquals(Long.valueOf(1L), firstBill.getId());
		assertEquals(95.0, firstBill.getTotalAmount(), 0.001);
		assertEquals(9.5, firstBill.getDiscountAmount(), 0.001);
		assertEquals(85.5, firstBill.getNetAmount(), 0.001);
		assertEquals(BillStatus.PAID, firstBill.getBillStatus());
		assertEquals("Paid", firstBill.getBillStatus().getDisplayName());
		assertEquals(customer, firstBill.getCustomer());
		assertEquals(testUser, firstBill.getUser());
	}

	@Test
	public void testAllBillStatusTypes() {
		assertEquals(BillStatus.PAID, customer.getBillList().get(0).getBillStatus());
		assertEquals(BillStatus.PENDING, customer.getBillList().get(1).getBillStatus());
		assertEquals(BillStatus.DRAFT, customer.getBillList().get(2).getBillStatus());
	}

	@Test
	public void testBillStatusDisplayNames() {
		assertEquals("Paid", customer.getBillList().get(0).getBillStatus().getDisplayName());
		assertEquals("Pending", customer.getBillList().get(1).getBillStatus().getDisplayName());
		assertEquals("Draft", customer.getBillList().get(2).getBillStatus().getDisplayName());
	}

	@Test
	public void testBillItemsThroughBills() {
		Bill firstBill = customer.getBillList().get(0);
		List<BillItem> billItems = firstBill.getBillItems();

		assertNotNull(billItems);
		assertEquals(2, billItems.size());

		BillItem firstItem = billItems.get(0);
		assertEquals(Long.valueOf(1L), firstItem.getId());
		assertEquals(Integer.valueOf(2), firstItem.getQuantity());
		assertEquals(25.0, firstItem.getUnitPrice(), 0.001);
		assertEquals(50.0, firstItem.getSubTotal(), 0.001);
		assertEquals(5.0, firstItem.getDiscountAmount(), 0.001);
		assertEquals(firstBill, firstItem.getBill());
		assertEquals(testItem1, firstItem.getItem());

		BillItem secondItem = billItems.get(1);
		assertEquals(Long.valueOf(2L), secondItem.getId());
		assertEquals(Integer.valueOf(3), secondItem.getQuantity());
		assertEquals(15.0, secondItem.getUnitPrice(), 0.001);
		assertEquals(45.0, secondItem.getSubTotal(), 0.001);
		assertEquals(4.5, secondItem.getDiscountAmount(), 0.001);
		assertEquals(testItem2, secondItem.getItem());
	}

	@Test
	public void testBillItemCalculations() {
		BillItem billItem = customer.getBillList().get(0).getBillItems().get(0);

		// Test that subTotal = quantity * unitPrice
		double expectedSubTotal = billItem.getQuantity() * billItem.getUnitPrice();
		assertEquals(expectedSubTotal, billItem.getSubTotal(), 0.001);

		// Test discount is applied correctly
		assertTrue(billItem.getDiscountAmount() <= billItem.getSubTotal());
	}

//	@Test
//	public void testBillItemTimestamps() {
//
//		BillItem billItem = customer.getBillList().get(0).getBillItems().get(0);
//		assertNotNull(billItem.getCreatedAt());
//		assertTrue(billItem.getCreatedAt().is(LocalDateTime.now().plusSeconds(1)));
//	}

	@Test
	public void testAddNewBill() {
		Bill newBill = new Bill();
		newBill.setId(4L);
		newBill.setTotalAmount(200.0);
		newBill.setDiscountAmount(20.0);
		newBill.setNetAmount(180.0);
		newBill.setBillStatus(BillStatus.CANCELLED);
		newBill.setUser(testUser);
		newBill.setCustomer(customer);

		customer.getBillList().add(newBill);

		assertEquals(4, customer.getBillList().size());
		Bill addedBill = customer.getBillList().get(3);
		assertEquals(Long.valueOf(4L), addedBill.getId());
		assertEquals(BillStatus.CANCELLED, addedBill.getBillStatus());
		assertEquals("Cancelled", addedBill.getBillStatus().getDisplayName());
	}

	@Test
	public void testRemoveBill() {
		Bill billToRemove = customer.getBillList().get(0);
		customer.getBillList().remove(billToRemove);

		assertEquals(2, customer.getBillList().size());
		assertEquals(Long.valueOf(2L), customer.getBillList().get(0).getId());
	}

	@Test
	public void testCustomerTotalBilledAmount() {
		double totalBilled = customer.getBillList().stream()
				.mapToDouble(Bill::getTotalAmount)
				.sum();

		assertEquals(320.0, totalBilled, 0.001); // 95 + 150 + 75
	}

	@Test
	public void testCustomerTotalNetAmount() {
		double totalNetAmount = customer.getBillList().stream()
				.mapToDouble(Bill::getNetAmount)
				.sum();

		assertEquals(295.5, totalNetAmount, 0.001); // 85.5 + 135 + 75
	}

	@Test
	public void testCustomerTotalDiscount() {
		double totalDiscount = customer.getBillList().stream()
				.mapToDouble(Bill::getDiscountAmount)
				.sum();

		assertEquals(24.5, totalDiscount, 0.001); // 9.5 + 15 + 0
	}

	@Test
	public void testBillStatusCounts() {
		long paidCount = customer.getBillList().stream()
				.filter(bill -> BillStatus.PAID.equals(bill.getBillStatus()))
				.count();

		long pendingCount = customer.getBillList().stream()
				.filter(bill -> BillStatus.PENDING.equals(bill.getBillStatus()))
				.count();

		long draftCount = customer.getBillList().stream()
				.filter(bill -> BillStatus.DRAFT.equals(bill.getBillStatus()))
				.count();

		assertEquals(1, paidCount);
		assertEquals(1, pendingCount);
		assertEquals(1, draftCount);
	}

	@Test
	public void testBillAmountCalculations() {
		Bill bill = customer.getBillList().get(0);

		// Verify net amount calculation
		assertEquals(bill.getNetAmount(),
				bill.getTotalAmount() - bill.getDiscountAmount(),
				0.001);
	}

	@Test
	public void testBillItemTotalCalculation() {
		Bill bill = customer.getBillList().get(0);
		List<BillItem> items = bill.getBillItems();

		double calculatedTotal = items.stream()
				.mapToDouble(BillItem::getSubTotal)
				.sum();

		double calculatedDiscount = items.stream()
				.mapToDouble(BillItem::getDiscountAmount)
				.sum();

		assertEquals(bill.getTotalAmount(), calculatedTotal, 0.001);
		assertEquals(bill.getDiscountAmount(), calculatedDiscount, 0.001);
	}

	@Test
	public void testAllBillStatusValues() {
		// Test that we can access all enum values
		assertEquals(5, BillStatus.values().length);
		assertEquals("Draft", BillStatus.DRAFT.getDisplayName());
		assertEquals("Pending", BillStatus.PENDING.getDisplayName());
		assertEquals("Paid", BillStatus.PAID.getDisplayName());
		assertEquals("Cancelled", BillStatus.CANCELLED.getDisplayName());
		assertEquals("Refunded", BillStatus.REFUNDED.getDisplayName());
	}

	@Test
	public void testBillItemBidirectionalRelationships() {
		BillItem billItem = customer.getBillList().get(0).getBillItems().get(0);

		assertEquals(customer.getBillList().get(0), billItem.getBill());
		assertEquals(testItem1, billItem.getItem());

		// Test that item knows about bill items through its own relationships
		assertNotNull(billItem.getItem().getTitle());
		assertEquals("Test Item 1", billItem.getItem().getTitle());
	}

	@Test
	public void testCustomerBillingSummary() {
		long totalBills = customer.getBillList().size();
		double totalAmount = customer.getBillList().stream()
				.mapToDouble(Bill::getTotalAmount)
				.sum();
		double totalPaid = customer.getBillList().stream()
				.filter(bill -> BillStatus.PAID.equals(bill.getBillStatus()))
				.mapToDouble(Bill::getTotalAmount)
				.sum();
		double totalPending = customer.getBillList().stream()
				.filter(bill -> BillStatus.PENDING.equals(bill.getBillStatus()))
				.mapToDouble(Bill::getTotalAmount)
				.sum();

		assertEquals(3, totalBills);
		assertEquals(320.0, totalAmount, 0.001);
		assertEquals(95.0, totalPaid, 0.001);
		assertEquals(150.0, totalPending, 0.001);
	}

	@Test
	public void testBillStatusTransitions() {
		Bill draftBill = customer.getBillList().get(2);
		assertEquals(BillStatus.DRAFT, draftBill.getBillStatus());

		// Simulate status changes through workflow
		draftBill.setBillStatus(BillStatus.PENDING);
		assertEquals(BillStatus.PENDING, draftBill.getBillStatus());

		draftBill.setBillStatus(BillStatus.PAID);
		assertEquals(BillStatus.PAID, draftBill.getBillStatus());

		draftBill.setBillStatus(BillStatus.REFUNDED);
		assertEquals(BillStatus.REFUNDED, draftBill.getBillStatus());
	}

	@Test
	public void testEmptyBillItems() {
		Bill billWithoutItems = customer.getBillList().get(1); // Second bill has no items
		assertNull(billWithoutItems.getBillItems()); // Or empty list depending on implementation

		// If bill items were initialized as empty list:
		// assertNotNull(billWithoutItems.getBillItems());
		// assertTrue(billWithoutItems.getBillItems().isEmpty());
	}
}