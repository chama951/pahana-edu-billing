package com.pahana.edu.dao;

import java.sql.SQLException;

import com.pahana.edu.model.User;

public interface UserDao {

	void createUser(User user) throws SQLException;

	User getUserByUsername(String username) throws SQLException;
}
