package com.pahana.edu.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.pahana.edu.model.User;

public interface UserDao {

	void createUser(User user) throws SQLException;

	User getUserByUsername(String username) throws SQLException;

	boolean checkUserByUsername(String username, Long userId);

	User getUserById(Long id) throws SQLException;

	void updateLastLogin(Long loggedInUserId, LocalDateTime lastLoginTime) throws SQLException;

	void updateUser(User userToUpdate) throws SQLException;

	void updateUsername(Long id, String newUsername) throws SQLException;

	List<User> getAllUsers() throws SQLException;

	void deleteUser(Long userId) throws SQLException;

	boolean updatePassword(Long id, String hashPassword) throws SQLException;
}
