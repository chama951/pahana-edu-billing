package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pahana.edu.dao.BillDao;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.model.enums.UserRole;

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
	public List<Bill> getAllBills() throws SQLException {
		String sql = "SELECT "
				+ "b.id, b.totalAmount, b.netAmount, b.discountAmount, b.createdAt, b.billStatus, "
				+ "c.id as customerId, c.accountNumber as customerAccountNumber, "
				+ "c.firstName as customerFirstName, c.lastName as customerLastName, "
				+ "c.address as customerAddress, c.phoneNumber as customerPhoneNumber, "
				+ "c.email as customerEmail, c.unitsConsumed as customerUnitsConsumed, "
				+ "c.createdAt as customerCreatedAt, c.updatedAt as customerUpdatedAt, "
				+ "u.id as userId, u.username as userUsername, u.role as userRole, "
				+ "u.isActive as userIsActive, u.createdAt as userCreatedAt, "
				+ "u.updatedAt as userUpdatedAt, u.lastLogin as userLastLogin "
				+ "FROM bill b "
				+ "JOIN customer c ON b.customerId = c.id "
				+ "JOIN user u ON b.userId = u.id";

		try (PreparedStatement stmt = connection.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			List<Bill> billList = new ArrayList<>();
			while (rs.next()) {
				billList.add(mapBill(rs));
			}
			return billList;
		} catch (SQLException e) {
			throw new RuntimeException("Database error occurred", e);
		}
	}

	@Override
	public Bill getBillById(Long billId) throws SQLException {
		String sql = "SELECT "
				+ "b.id, b.totalAmount, b.netAmount, b.discountAmount, b.createdAt, b.billStatus, "
				+ "c.id as customerId, c.accountNumber as customerAccountNumber, "
				+ "c.firstName as customerFirstName, c.lastName as customerLastName, "
				+ "c.address as customerAddress, c.phoneNumber as customerPhoneNumber, "
				+ "c.email as customerEmail, c.unitsConsumed as customerUnitsConsumed, "
				+ "c.createdAt as customerCreatedAt, c.updatedAt as customerUpdatedAt, "
				+ "u.id as userId, u.username as userUsername, u.role as userRole, "
				+ "u.isActive as userIsActive, u.createdAt as userCreatedAt, "
				+ "u.updatedAt as userUpdatedAt, u.lastLogin as userLastLogin "
				+ "FROM bill b "
				+ "JOIN customer c ON b.customerId = c.id "
				+ "JOIN user u ON b.userId = u.id WHERE b.id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, billId);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? mapBill(rs) : null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Database error occurred", e);
		}
	}

	private Bill mapBill(ResultSet rs) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getLong("id"));
		bill.setTotalAmount(rs.getDouble("totalAmount"));
		bill.setNetAmount(rs.getDouble("netAmount"));
		bill.setDiscountAmount(rs.getDouble("discountAmount"));
		bill.setBillStatus(BillStatus.valueOf(rs.getString("billStatus")));
		bill.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));

		// Map Customer with all fields
		Customer customer = new Customer();
		customer.setId(rs.getLong("customerId"));
		customer.setAccountNumber(rs.getLong("customerAccountNumber"));
		customer.setFirstName(rs.getString("customerFirstName"));
		customer.setLastName(rs.getString("customerLastName"));
		customer.setAddress(rs.getString("customerAddress"));
		customer.setPhoneNumber(rs.getString("customerPhoneNumber"));
		customer.setEmail(rs.getString("customerEmail"));
		customer.setUnitsConsumed(rs.getInt("customerUnitsConsumed"));
		customer.setCreatedAt(rs.getObject("customerCreatedAt", LocalDateTime.class));
		customer.setUpdatedAt(rs.getObject("customerUpdatedAt", LocalDateTime.class));
		bill.setCustomer(customer);

		// Map User with all fields (excluding hashedPassword for security)
		User user = new User();
		user.setId(rs.getLong("userId"));
		user.setUsername(rs.getString("userUsername"));
		user.setRole(UserRole.valueOf(rs.getString("userRole")));
		user.setIsActive(rs.getBoolean("userIsActive"));
		user.setCreatedAt(rs.getObject("userCreatedAt", LocalDateTime.class));
		user.setUpdatedAt(rs.getObject("userUpdatedAt", LocalDateTime.class));
		user.setLastLogin(rs.getObject("userLastLogin", LocalDateTime.class));
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

	@Override
	public void updateBill(Bill billInDb) throws SQLException {
		String sql = "UPDATE bill SET "
				+ "totalAmount = ?, "
				+ "discountAmount = ?, "
				+ "netAmount = ?, "
				+ "createdAt = ?, "
//				+ "customerId = ?, "
//				+ "userId = ?, "
				+ "billStatus = ? "
				+ "WHERE id = ?"; // assuming there's an id column as primary key

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, billInDb.getTotalAmount());
			stmt.setDouble(2, billInDb.getDiscountAmount());
			stmt.setDouble(3, billInDb.getNetAmount());
			stmt.setObject(4, billInDb.getCreatedAt()); // using bill's createdAt instead of current time
//			stmt.setLong(5, customerId);
//			stmt.setLong(6, userId);
			stmt.setString(5, billInDb.getBillStatus().getDisplayName());
			stmt.setLong(6, billInDb.getId()); // assuming Bill has getId() method
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}

	}

}
