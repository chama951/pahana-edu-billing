package com.pahana.edu.dao;

import java.sql.SQLException;

import com.pahana.edu.model.User;

public interface UserDao {

	void createUser(User user) throws SQLException;

	User getUserByUsername(String username) throws SQLException;

	User getUserById(Long id) throws SQLException;

	void updateLastLogin(Long loggedInUserId) throws SQLException;

	void updateUserByAdmin(User userToUpdate) throws SQLException;
	
	void updateUsernameBySelf(User userToUpdate) throws SQLException;
	
	void changePasswordBySelf(User loggedIn) throws SQLException;
}
