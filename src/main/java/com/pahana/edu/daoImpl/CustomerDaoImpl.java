package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;

public class CustomerDaoImpl implements CustomerDao {

	private final Connection connection;

	public CustomerDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void createCustomer(Customer customer) throws SQLException {
		String sql = "INSERT INTO customer ("
				+ "accountNumber,"
				+ "firstName,"
				+ "lastName,"
				+ "address,"
				+ "phoneNumber,"
				+ "email,"
				+ "unitsConsumed,"
				+ "createdAt) VALUES (?,?,?,?,?,?,?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, customer.getAccountNumber());
			stmt.setString(2, customer.getFirstName());
			stmt.setString(3, customer.getLastName());
			stmt.setString(4, customer.getAddress());
			stmt.setString(5, customer.getPhoneNumber());
			stmt.setString(6, customer.getEmail());
			stmt.setInt(7, customer.getUnitsConsumed());
			stmt.setObject(8, LocalDateTime.now());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws SQLException {
		String sql = "SELECT  "
				+ "/* Customer fields */"
				+ " c.id AS customerid,"
				+ " c.accountNumber AS accountNumber, "
				+ " c.firstName AS firstName, "
				+ " c.lastName AS lastName, "
				+ " c.address AS address, "
				+ " c.phoneNumber AS phoneNumber, "
				+ " c.email AS email, "
				+ " c.unitsConsumed AS unitsConsumed, "
				+ " c.createdAt AS createdAt, "
				+ " c.updatedAt AS updatedAt, "
				+ "/* Bill fields */ "
				+ " b.id AS billId, "
				+ " b.billDate AS billDate, "
				+ " b.totalAmount AS totalAmount, "
				+ " b.discountAmount AS discountAmount, "
				+ " b.createdAt AS createdAt, "
				+ " b.updatedAt AS updatedAt, "
				+ "/* BillItem fields */ "
				+ " bi.id AS billItemId, "
				+ " bi.quantity AS quantity, "
				+ " bi.unitPrice AS unitPrice, "
				+ " bi.subTotal AS subTotal, "
				+ " bi.discountPercentage AS discountPercentage, "
				+ " bi.createdAt AS createdAt, "
				+ " bi.updatedAt AS updatedAt, "
				+ "/* Item fields */ "
				+ " i.id AS itemId, "
				+ " i.title AS title, "
				+ " i.description AS description, "
				+ " i.price AS price, "
				+ " i.quantityInStock AS quantityInStock, "
				+ " i.author AS author, "
				+ " i.publicationYear AS publicationYear, "
				+ " i.publisher AS publisher, "
				+ " i.createdAt AS createdAt, "
				+ " i.updatedAt AS updatedAt "
				+ " FROM customer c "
				+ " LEFT JOIN bill b ON c.id = b.customerId "
				+ " LEFT JOIN billItem bi ON b.id = bi.billId "
				+ " LEFT JOIN item i ON bi.itemId = i.id "
				+ " ORDER BY c.id, b.id, bi.id";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			List<Customer> customers = new ArrayList<>();
			Customer currentCustomer = null;
			Bill currentBill = null;
			Long lastCustomerId = null;
			Long lastBillId = null;

			while (rs.next()) {
				Long customerId = rs.getLong("customerid");

				// Handle new customer
				if (!customerId.equals(lastCustomerId)) {
					currentCustomer = mapCustomer(rs);
					customers.add(currentCustomer);
					lastCustomerId = customerId;
					lastBillId = null; // Reset bill tracking
				}

				// Handle bills (skip if no bill exists)
				if (rs.getObject("billId") != null) {
					Long billId = rs.getLong("billId");

					// Handle new bill
					if (!billId.equals(lastBillId)) {
						currentBill = mapBill(rs);
						currentCustomer.getBillList().add(currentBill);
						lastBillId = billId;
					}

					// Handle bill items (skip if no item exists)
					if (rs.getObject("billItemId") != null) {
						BillItem item = mapBillItem(rs);
						currentBill.getBillItems().add(item);

						// Handle item details
						if (rs.getObject("itemId") != null) {
							Item itemDetails = mapItem(rs);
							item.setItem(itemDetails);
						}
					}
				}
			}
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer getUserByAccNo(Long accNo) throws SQLException {
		String sql = "SELECT * FROM customer WHERE accountNumber = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, accNo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Customer customer = new Customer();
				customer = mapCustomer(rs);
				return customer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Customer mapCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("customerid"));
		customer.setAccountNumber(rs.getLong("accountNumber"));
		customer.setFirstName(rs.getString("firstName"));
		customer.setLastName(rs.getString("lastName"));
		customer.setAddress(rs.getString("address"));
		customer.setPhoneNumber(rs.getString("phoneNumber"));
		customer.setEmail(rs.getString("email"));
		customer.setUnitsConsumed(rs.getInt("unitsConsumed"));
		customer.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		customer.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		customer.setBillList(new ArrayList<>());
		return customer;
	}

	private Bill mapBill(ResultSet rs) throws SQLException {
		Bill bill = new Bill();
		bill.setId(rs.getLong("id"));
		bill.setBillDate(rs.getObject("billDate", LocalDateTime.class));
		bill.setTotalAmount(rs.getBigDecimal("totalAmount"));
		bill.setDiscountAmount(rs.getBigDecimal("discountAmount"));
		bill.setCreatedAt(rs.getObject("CreatedAt", LocalDateTime.class));
		bill.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		bill.setBillItems(new ArrayList<>());
		return bill;
	}

	private BillItem mapBillItem(ResultSet rs) throws SQLException {
		BillItem item = new BillItem();
		item.setId(rs.getLong("id"));
		item.setQuantity(rs.getInt("quantity"));
		item.setUnitPrice(rs.getDouble("unitPrice"));
		item.setSubTotal(rs.getDouble("subTotal"));
		item.setDiscountPercentage(rs.getDouble("discountPercentage"));
		item.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		item.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		return item;
	}

	private Item mapItem(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setId(rs.getLong("id"));
		item.setTitle(rs.getString("title"));
		item.setDescription(rs.getString("description"));
		item.setPrice(rs.getDouble("price"));
		item.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
		item.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
		item.setQuantityInStock(rs.getInt("quantityInStock"));
		item.setAuthor(rs.getString("author"));
		item.setPublicationYear(rs.getInt("publicationYear"));
		item.setPublisher("publisher");
		return item;
	}

	@Override
	public Customer getCustomerById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(Long id) throws SQLException {
		// TODO Auto-generated method stub

	}

}
