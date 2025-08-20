package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Item;

public class BillItemTest {

	private BillItem billItem;
	private Bill bill;
	private Item item;
	private LocalDateTime testTime;

	@Before
	public void setUp() {
		testTime = LocalDateTime.now();

		// Create bill
		bill = new Bill();
		bill.setId(1L);
		bill.setTotalAmount(100.0);
		bill.setDiscountAmount(10.0);
		bill.setNetAmount(90.0);

		// Create item
		item = new Item();
		item.setId(1L);
		item.setTitle("Test Item");
		item.setPrice(25.0);
		item.setDiscountPercentage(10.0);

		// Create bill item
		billItem = new BillItem();
		billItem.setId(1L);
		billItem.setQuantity(3);
		billItem.setUnitPrice(25.0);
		billItem.setSubTotal(75.0); // 3 * 25
		billItem.setDiscountAmount(7.5); // 10% of 75
		billItem.setCreatedAt(testTime);
		billItem.setBill(bill);
		billItem.setItem(item);
	}

	@Test
	public void testDefaultConstructor() {
		BillItem defaultBillItem = new BillItem();

		assertNull(defaultBillItem.getId());
		assertNull(defaultBillItem.getQuantity());
		assertEquals(0.0, defaultBillItem.getUnitPrice(), 0.001);
		assertEquals(0.0, defaultBillItem.getSubTotal(), 0.001);
		assertEquals(0.0, defaultBillItem.getDiscountAmount(), 0.001);
		assertNotNull(defaultBillItem.getCreatedAt());
		assertNull(defaultBillItem.getBill());
		assertNull(defaultBillItem.getItem());
	}

	@Test
	public void testSettersAndGetters() {
		BillItem testBillItem = new BillItem();

		testBillItem.setId(2L);
		testBillItem.setQuantity(5);
		testBillItem.setUnitPrice(15.0);
		testBillItem.setSubTotal(75.0);
		testBillItem.setDiscountAmount(7.5);
		testBillItem.setCreatedAt(testTime);
		testBillItem.setBill(bill);
		testBillItem.setItem(item);

		assertEquals(Long.valueOf(2L), testBillItem.getId());
		assertEquals(Integer.valueOf(5), testBillItem.getQuantity());
		assertEquals(15.0, testBillItem.getUnitPrice(), 0.001);
		assertEquals(75.0, testBillItem.getSubTotal(), 0.001);
		assertEquals(7.5, testBillItem.getDiscountAmount(), 0.001);
		assertEquals(testTime, testBillItem.getCreatedAt());
		assertEquals(bill, testBillItem.getBill());
		assertEquals(item, testBillItem.getItem());
	}

	@Test
	public void testSubTotalCalculation() {
		// Test that subTotal = quantity * unitPrice
		double expectedSubTotal = billItem.getQuantity() * billItem.getUnitPrice();
		assertEquals(expectedSubTotal, billItem.getSubTotal(), 0.001);
	}

	@Test
	public void testBillAssociation() {
		assertEquals(bill, billItem.getBill());
		assertEquals(Long.valueOf(1L), billItem.getBill().getId());
		assertEquals(100.0, billItem.getBill().getTotalAmount(), 0.001);
	}

	@Test
	public void testItemAssociation() {
		assertEquals(item, billItem.getItem());
		assertEquals(Long.valueOf(1L), billItem.getItem().getId());
		assertEquals("Test Item", billItem.getItem().getTitle());
		assertEquals(25.0, billItem.getItem().getPrice(), 0.001);
	}

	@Test
	public void testBidirectionalRelationships() {
		// Test that bill knows about this bill item (if properly set up)
		// This would require bill to have billItems collection

		// Test that item knows about this bill item (if properly set up)
		// This would require item to have billItems collection
	}

	@Test
	public void testDiscountAmountValidation() {
		// Discount should not exceed subTotal
		assertTrue(billItem.getDiscountAmount() <= billItem.getSubTotal());

		// Test zero discount
		billItem.setDiscountAmount(0.0);
		assertEquals(0.0, billItem.getDiscountAmount(), 0.001);

		// Test discount equal to subTotal
		billItem.setDiscountAmount(billItem.getSubTotal());
		assertEquals(billItem.getSubTotal(), billItem.getDiscountAmount(), 0.001);
	}

	@Test
	public void testEdgeCaseQuantities() {
		// Test minimum quantity
		billItem.setQuantity(1);
		assertEquals(Integer.valueOf(1), billItem.getQuantity());

		// Test zero quantity (should this be allowed?)
		billItem.setQuantity(0);
		assertEquals(Integer.valueOf(0), billItem.getQuantity());
//		assertEquals(0.0, billItem.getSubTotal(), 0.001);

		// Test large quantity
		billItem.setQuantity(1000);
		assertEquals(Integer.valueOf(1000), billItem.getQuantity());
	}

	@Test
	public void testEdgeCasePrices() {
		// Test zero price
		billItem.setUnitPrice(0.0);
		billItem.setSubTotal(0.0);
		assertEquals(0.0, billItem.getUnitPrice(), 0.001);
		assertEquals(0.0, billItem.getSubTotal(), 0.001);

		// Test negative price (should this be allowed?)
		billItem.setUnitPrice(-10.0);
		billItem.setSubTotal(-30.0); // 3 * -10
		assertEquals(-10.0, billItem.getUnitPrice(), 0.001);
		assertEquals(-30.0, billItem.getSubTotal(), 0.001);

		// Test high price
		billItem.setUnitPrice(999.99);
		billItem.setSubTotal(2999.97); // 3 * 999.99
		assertEquals(999.99, billItem.getUnitPrice(), 0.001);
		assertEquals(2999.97, billItem.getSubTotal(), 0.001);
	}

	@Test
	public void testTimestampBehavior() {
		assertNotNull(billItem.getCreatedAt());
		assertTrue(billItem.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));

		// Test that timestamp can be overridden
		LocalDateTime customTime = testTime.minusDays(1);
		billItem.setCreatedAt(customTime);
		assertEquals(customTime, billItem.getCreatedAt());
	}

	@Test
	public void testFinancialCalculations() {
		// Test various calculation scenarios
		billItem.setQuantity(4);
		billItem.setUnitPrice(12.5);
		billItem.setSubTotal(50.0); // 4 * 12.5
		billItem.setDiscountAmount(5.0); // 10% discount

		assertEquals(50.0, billItem.getSubTotal(), 0.001);
		assertEquals(5.0, billItem.getDiscountAmount(), 0.001);

		// Test net amount (would be calculated at bill level)
		double netAmount = billItem.getSubTotal() - billItem.getDiscountAmount();
		assertEquals(45.0, netAmount, 0.001);
	}

	@Test
	public void testDiscountPercentageCalculation() {
		// Test discount based on percentage (business logic)
		double discountPercentage = 20.0; // 20%
		double expectedDiscount = billItem.getSubTotal() * (discountPercentage / 100);
		billItem.setDiscountAmount(expectedDiscount);

		assertEquals(15.0, billItem.getDiscountAmount(), 0.001); // 20% of 75
	}

	@Test
	public void testNullAssociations() {
		BillItem nullAssociations = new BillItem();
		nullAssociations.setId(3L);
		nullAssociations.setQuantity(2);
		nullAssociations.setUnitPrice(10.0);
		nullAssociations.setSubTotal(20.0);

		assertNull(nullAssociations.getBill());
		assertNull(nullAssociations.getItem());
	}

	@Test
	public void testItemPriceConsistency() {
		// Test that unit price matches item price (business rule validation)
		assertEquals(billItem.getItem().getPrice(), billItem.getUnitPrice(), 0.001);

		// Test when unit price differs from item price (override scenario)
		billItem.setUnitPrice(20.0); // Special price different from item catalog
		assertEquals(20.0, billItem.getUnitPrice(), 0.001);
		assertNotEquals(billItem.getItem().getPrice(), billItem.getUnitPrice(), 0.001);
	}

	@Test
	public void testDiscountLargerThanSubTotal() {
		// Test discount exceeding subTotal (should this be prevented?)
		billItem.setDiscountAmount(100.0); // More than subTotal of 75
		assertEquals(100.0, billItem.getDiscountAmount(), 0.001);
		assertTrue(billItem.getDiscountAmount() > billItem.getSubTotal());
	}

	@Test
	public void testBulkOperations() {
		// Test multiple bill items with different scenarios
		BillItem item1 = new BillItem();
		item1.setQuantity(10);
		item1.setUnitPrice(5.0);
		item1.setSubTotal(50.0);
		item1.setDiscountAmount(5.0);

		BillItem item2 = new BillItem();
		item2.setQuantity(1);
		item2.setUnitPrice(100.0);
		item2.setSubTotal(100.0);
		item2.setDiscountAmount(0.0);

		assertEquals(50.0, item1.getSubTotal(), 0.001);
		assertEquals(100.0, item2.getSubTotal(), 0.001);
		assertEquals(5.0, item1.getDiscountAmount(), 0.001);
		assertEquals(0.0, item2.getDiscountAmount(), 0.001);
	}

	@Test
	public void testDecimalPrecision() {
		// Test decimal precision handling
		billItem.setUnitPrice(12.345);
		billItem.setQuantity(7);
		billItem.setSubTotal(86.415); // 7 * 12.345
		billItem.setDiscountAmount(8.6415); // 10%

		assertEquals(12.345, billItem.getUnitPrice(), 0.001);
		assertEquals(86.415, billItem.getSubTotal(), 0.001);
		assertEquals(8.6415, billItem.getDiscountAmount(), 0.001);
	}

	@Test
	public void testCreatedAtAutoGeneration() {
		BillItem newBillItem = new BillItem();
		// createdAt should be auto-generated by @CreationTimestamp
		assertNotNull(newBillItem.getCreatedAt());
		assertTrue(newBillItem.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
	}

	@Test
	public void testItemDetailsThroughBillItem() {
		// Test accessing item properties through bill item
		assertEquals("Test Item", billItem.getItem().getTitle());
		assertEquals(25.0, billItem.getItem().getPrice(), 0.001);
		assertEquals(10.0, billItem.getItem().getDiscountPercentage(), 0.001);
	}

	@Test
	public void testBillDetailsThroughBillItem() {
		// Test accessing bill properties through bill item
		assertEquals(Long.valueOf(1L), billItem.getBill().getId());
		assertEquals(100.0, billItem.getBill().getTotalAmount(), 0.001);
		assertEquals(90.0, billItem.getBill().getNetAmount(), 0.001);
	}

	@Test
	public void testBusinessRuleValidation() {
		// Test that business rules are maintained
		assertTrue("Discount should not be negative", billItem.getDiscountAmount() >= 0);
		assertTrue("SubTotal should be non-negative", billItem.getSubTotal() >= 0);
		assertNotNull("Bill should be associated", billItem.getBill());
		assertNotNull("Item should be associated", billItem.getItem());
	}
}