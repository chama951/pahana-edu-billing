package com.pahana.edu.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.pahana.edu.model.User;

public interface UserDao {

	User createUser(User user) throws SQLException;

	User getUserByUsername(String username) throws SQLException;

	User getUserById(Long id) throws SQLException;

	void updateLastLogin(Long loggedInUserId, LocalDateTime lastLoginTime) throws SQLException;

	void updateUser(User userToUpdate) throws SQLException;

	void changeUsername(Long id, String newUsername) throws SQLException;

	List<User> getAllUsers() throws SQLException;

	void deleteUser(Long userId) throws SQLException;

	boolean changePassword(Long id, String hashPassword) throws SQLException;

	boolean existByUsername(String username, Long userId) throws SQLException;

}
