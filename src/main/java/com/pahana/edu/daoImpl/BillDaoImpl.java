package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.pahana.edu.dao.BillDao;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;

public class BillDaoImpl implements BillDao {

	private final Connection connection;

	public BillDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Long createBill(Bill bill, Long customerId, Long userId) throws SQLException {
		String sql = "INSERT INTO bill ("
				+ "totalAmount,"
				+ "discountAmount,"
				+ "netAmount, "
				+ "createdAt,"
				+ "customerId,"
				+ "userId, "
				+ "billStatus ) VALUES (?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setDouble(1, bill.getTotalAmount());
			stmt.setDouble(2, bill.getDiscountAmount());
			stmt.setDouble(3, bill.getNetAmount());
			stmt.setObject(4, LocalDateTime.now());
			stmt.setLong(5, customerId);
			stmt.setLong(6, userId);
			stmt.setString(7, bill.getBillStatus().getDisplayName());
			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
		return null;
	}

	@Override
	public Bill getBillById(Long billId) throws SQLException {
		String sql = "SELECT "
				+ "b.id, b.totalAmount, b.netAmount, b.discountAmount, b.createdAt, b.billStatus, "
				+ "c.id, c.accountNumber, c.firstName, c.lastName, c.address, "
				+ "c.phoneNumber, c.email, c.unitsConsumed, "
				+ "u.id, u.username, u.hashedPassword, u.role, u.isActive "
				+ "FROM bill b "
				+ "JOIN customer c ON b.customerId = c.id "
				+ "JOIN user u ON b.userId = u.id WHERE b.id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, billId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Bill bill = new Bill();
				bill = mapBill(rs);
				return bill;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
		return null;
	}

	public Bill mapBill(ResultSet rs) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getLong("b.id")); // 'b' prefix for bill columns
		bill.setTotalAmount(rs.getDouble("b.totalAmount"));
		bill.setNetAmount(rs.getDouble("b.netAmount"));
		bill.setDiscountAmount(rs.getDouble("b.discountAmount"));
		bill.setBillStatus(BillStatus.valueOf(rs.getString("b.billStatus")));
		bill.setCreatedAt(rs.getObject("b.createdAt", LocalDateTime.class));

		// Map Customer (with 'c' prefix)
		Customer customer = new Customer();
		customer.setId(rs.getLong("c.id"));
		customer.setAccountNumber(rs.getLong("c.accountNumber"));
		customer.setFirstName(rs.getString("c.firstName"));
		customer.setLastName(rs.getString("c.lastName"));
		customer.setAddress(rs.getString("c.address"));
		customer.setPhoneNumber(rs.getString("c.phoneNumber"));
		customer.setEmail(rs.getString("c.email"));
		customer.setUnitsConsumed(rs.getInt("c.unitsConsumed"));
		bill.setCustomer(customer);

		// Map User (with 'u' prefix)
		User user = new User();
		user.setId(rs.getLong("u.id"));
		user.setUsername(rs.getString("u.username"));
		user.setIsActive(rs.getBoolean("u.isActive"));
		bill.setUser(user);

		return bill;
	}

	@Override
	public void createBillItem(BillItem billItem, Long billId) throws SQLException {
		String sql = "INSERT INTO billItem ("
				+ "quantity,"
				+ "unitPrice,"
				+ "subTotal, "
				+ "discountAmount,"
				+ "billId,"
				+ "createdAt, "
				+ "itemId) VALUES (?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, billItem.getQuantity());
			stmt.setDouble(2, billItem.getUnitPrice());
			stmt.setDouble(3, billItem.getSubTotal());
			stmt.setDouble(4, billItem.getDiscountAmount());
			stmt.setLong(5, billId);
			stmt.setObject(6, LocalDateTime.now());
			stmt.setLong(7, billItem.getItem().getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
	}

}
