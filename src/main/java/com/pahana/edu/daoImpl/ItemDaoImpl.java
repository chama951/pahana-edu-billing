package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pahana.edu.dao.ItemDao;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.UserRole;

public class ItemDaoImpl implements ItemDao {

	private final Connection connection;

	public ItemDaoImpl(Connection connection) {
		this.connection = connection;
	}

	public void updateItem(Item itemToUpdate) {
		String sql = "UPDATE item SET "
				+ "title = ?, "
				+ "isbn = ?, "
				+ "quantityInStock = ?, "
				+ "userId = ?, "
				+ "price = ?, "
				+ "description = ?, "
				+ "author = ?, "
				+ "publicationYear = ?, "
				+ "publisher = ?, "
				+ "updatedAt = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, itemToUpdate.getTitle());
			stmt.setString(2, itemToUpdate.getIsbn());
			stmt.setInt(3, itemToUpdate.getQuantityInStock());
			stmt.setLong(4, itemToUpdate.getUser().getId());
			stmt.setDouble(5, itemToUpdate.getPrice());
			stmt.setString(6, itemToUpdate.getDescription());
			stmt.setString(7, itemToUpdate.getAuthor());
			stmt.setInt(8, itemToUpdate.getPublicationYear());
			stmt.setString(9, itemToUpdate.getPublisher());
			stmt.setObject(10, LocalDateTime.now());
			stmt.setObject(11, itemToUpdate.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
	}

	@Override
	public List<Item> getAllItems() throws SQLException {

		String sql = "SELECT i.*, "
				+ "u.id as user_id, "
				+ "u.username, u.role, "
				+ "u.isActive "
				+ "FROM item i LEFT JOIN user u ON i.UserId = u.id";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			List<Item> items = new ArrayList<>();
			while (rs.next()) {
				Item item = mapItem(rs);

				if (rs.getObject("user_id") != null) {
					item.setUser(mapUser(rs));
				}

				items.add(item);
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
	}

	private User mapUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setRole(UserRole.valueOf(rs.getString("role")));
		user.setIsActive(rs.getBoolean("isActive"));
		return user;
	}

	private Item mapItem(ResultSet rs) throws SQLException {
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

	@Override
	public boolean existByIsbn(String isbn, Long id) throws SQLException {

		boolean isUpdate = false;
		String sql = "";

		if (id != null) {
			isUpdate = true;
			sql = "SELECT COUNT(*) FROM item WHERE isbn = ? AND id != ?";
			System.out.println("String isbn " + isbn);
		} else {
			sql = "SELECT COUNT(*) FROM item WHERE isbn = ?";
		}
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, isbn);
			if (isUpdate) {
				stmt.setLong(2, id);
			}
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // Returns true if count > 0
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
	}

	@Override
	public void createItem(Item newItem) throws SQLException {
		String sql = "INSERT INTO item ("
				+ "title,"
				+ "isbn,"
				+ "quantityInStock,"
				+ "userId,"
				+ "price, "
				+ "description,"
				+ "author,"
				+ "publicationYear,"
				+ "publisher, "
				+ "createdAt) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, newItem.getTitle());
			stmt.setString(2, newItem.getIsbn());
			stmt.setInt(3, newItem.getQuantityInStock());
			stmt.setLong(4, newItem.getUser().getId());
			stmt.setDouble(5, newItem.getPrice());
			stmt.setString(6, newItem.getDescription());
			stmt.setString(7, newItem.getAuthor());
			stmt.setInt(8, newItem.getPublicationYear());
			stmt.setString(9, newItem.getPublisher());
			stmt.setObject(10, LocalDateTime.now());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}
	}

	@Override
	public void deleteItem(Long itemId) throws SQLException {
		String sql = "DELETE FROM item WHERE id=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, itemId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error occurred", e);
		}

	}

}
