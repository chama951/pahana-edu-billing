package com.pahana.edu.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String sql = "INSERT INTO users (username, hashedPassword, role) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getHashedPassword()); // Already hashed
			stmt.setString(3, user.getRole().name());
			stmt.executeUpdate();
		}
	}

	@Override
	public User getUserByUsername(String username) throws SQLException {
		 String sql = "SELECT * FROM users WHERE username = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, username);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	            	User user = new User();
	            	user.setId( rs.getLong("id"));
	            	user.setUsername(rs.getString("username"));
	            	user.setHashedPassword(rs.getString("hashedPassword"));
					user.setRole(UserRole.valueOf(rs.getString("role")));
	            	return user;
	            }
	            return null;
	        }
	}

}
