package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.UserRole;

public class UserDaoImpl implements UserDao {

	private final Connection connection;

	public UserDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void createUser(User user) throws SQLException {
		String sql = "INSERT INTO user ("
				+ "username, "
				+ "hashedPassword, "
				+ "role, "
				+ "isActive, "
				+ "createdAt) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getHashedPassword()); // Already hashed
			stmt.setString(3, user.getRole().name());
			stmt.setBoolean(4, user.getIsActive());
			stmt.setObject(5, user.getCreatedAt());
			stmt.executeUpdate();
		}
	}

	@Override
	public User getUserByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user = mapUser(rs);
				return user;
			}
			return null;
		}
	}

	@Override
	public void updateLastLogin(Long loggedInUserId, LocalDateTime lastLoginTime) throws SQLException {
		String sql = "UPDATE user SET lastLogin = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, lastLoginTime);
			stmt.setObject(2, loggedInUserId);
			stmt.executeUpdate();
		}
	}

	@Override
	public User getUserById(Long id) throws SQLException {
		String sql = "SELECT * FROM user WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user = mapUser(rs);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUserByAdmin(User userToUpdate) throws SQLException {
		String sql = "UPDATE user SET "
				+ "username = ?, "
				+ "role = ?, "
				+ "isActive = ? , "
				+ "updatedAt = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, userToUpdate.getUsername());
			stmt.setString(2, userToUpdate.getRole().name());
			stmt.setBoolean(3, userToUpdate.getIsActive());
			stmt.setObject(4, LocalDateTime.now());
			stmt.setObject(5, userToUpdate.getId());
			stmt.executeUpdate();
		}
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		String sql = "SELECT * FROM user";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(mapUser(rs));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private User mapUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setHashedPassword(rs.getString("hashedPassword"));
		user.setRole(UserRole.valueOf(rs.getString("role")));
		user.setIsActive(rs.getBoolean("isActive"));

		LocalDateTime rtrvdTime;
		rtrvdTime = rs.getObject("createdAt", LocalDateTime.class);
		user.setCreatedAt(rtrvdTime);

		rtrvdTime = rs.getObject("lastLogin", LocalDateTime.class);
		user.setLastLogin(rtrvdTime);

		rtrvdTime = rs.getObject("updatedAt", LocalDateTime.class);
		user.setUpdatedAt(rtrvdTime);
		return user;
	}

	@Override
	public void deleteUser(Long userId) throws SQLException {
		String sql = "DELETE FROM user WHERE id=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, userId);
			stmt.executeUpdate();
		}
	}

	@Override
	public void updateUsername(Long id, String newUsername) throws SQLException {
		String sql = "UPDATE user SET "
				+ "username = ?, "
				+ "updatedAt = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, newUsername);
			stmt.setObject(2, LocalDateTime.now());
			stmt.setLong(3, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public boolean updatePassword(Long id, String hashPassword) throws SQLException {
		String sql = "UPDATE user SET "
				+ "hashedPassword = ?, "
				+ "updatedAt = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, hashPassword);
			stmt.setObject(2, LocalDateTime.now());
			stmt.setLong(3, id);
			stmt.executeUpdate();
		}
		return false;
	}

}
