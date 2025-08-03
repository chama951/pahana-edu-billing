package com.pahana.edu.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.Payment;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.PaymentMethod;
import com.pahana.edu.model.enums.UserRole;

public class PahanaEntityMapper {
	// Customer Mapper
	public Customer mapCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("id"));
		customer.setAccountNumber(rs.getLong("accountNumber"));
		customer.setFirstName(rs.getString("firstName"));
		customer.setLastName(rs.getString("lastName"));
		customer.setAddress(rs.getString("address"));
		customer.setPhoneNumber(rs.getString("phoneNumber"));
		customer.setEmail(rs.getString("email"));
		customer.setUnitsConsumed(rs.getInt("unitsConsumed"));
		customer.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		customer.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		return customer;
	}

	// Bill Mapper
	public Bill mapBill(ResultSet rs) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getLong("id"));
		bill.setBillDate(rs.getObject("billDate", LocalDateTime.class));
		bill.setTotalAmount(rs.getBigDecimal("totalAmount"));
		bill.setDiscountAmount(rs.getBigDecimal("discountAmount"));
		bill.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		bill.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		return bill;
	}

	// BillItem Mapper
	public BillItem mapBillItem(ResultSet rs) throws SQLException {
		BillItem billItem = new BillItem();
		billItem.setId(rs.getLong("id"));
		billItem.setQuantity(rs.getInt("quantity"));
		billItem.setUnitPrice(rs.getDouble("unitPrice"));
		billItem.setSubTotal(rs.getDouble("subTotal"));
		billItem.setDiscountPercentage(rs.getDouble("discountPercentage"));
		billItem.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		billItem.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		return billItem;
	}

	// Item Mapper
	public Item mapItem(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setId(rs.getLong("id"));
		item.setTitle(rs.getString("title"));
		item.setIsbn(rs.getString("isbn"));
		item.setPrice(rs.getDouble("price"));
		item.setQuantityInStock(rs.getInt("quantityInStock"));
		item.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		item.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		item.setDescription(rs.getString("description"));
		item.setAuthor(rs.getString("author"));
		item.setPublicationYear(rs.getInt("publicationYear"));
		item.setPublisher(rs.getString("publisher"));
		return item;
	}

	// User Mapper
	public User mapUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setHashedPassword(rs.getString("hashedPassword"));
		user.setRole(UserRole.valueOf(rs.getString("role")));
		user.setIsActive(rs.getBoolean("isActive"));
		user.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		user.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		user.setLastLogin(rs.getObject("lastLogin", LocalDateTime.class));
		return user;
	}

	// Payment Mapper
	public Payment mapPayment(ResultSet rs) throws SQLException {
		Payment payment = new Payment();
		payment.setId(rs.getLong("id"));
		payment.setAmount(rs.getDouble("amount"));
		payment.setPaymentDate(rs.getObject("paymentDate", LocalDateTime.class));
		payment.setNotes(rs.getString("notes"));
		payment.setPaymentMethod(PaymentMethod.valueOf(rs.getString("paymentMethod")));
		payment.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		payment.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		return payment;
	}
}
