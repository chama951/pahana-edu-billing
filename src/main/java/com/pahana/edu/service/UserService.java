package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.User;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.exception.MyValidationException;

public interface UserService {

	List<User> getAllUsers() throws SQLException;

	void updateUser(User userLoggedIn, User userToUpdate) throws SQLException, MyCustomException, MyValidationException;

	User createUser(User newUser) throws MyCustomException, SQLException, MyValidationException;

	void deleteUser(Long idToDelete, Long loggedInId) throws SQLException, MyCustomException;

	void changePassword(Long loggedInId, String passwordToUpdate)
			throws SQLException, MyCustomException, MyValidationException;

	void changeUsername(Long id, String newUsername) throws SQLException, MyCustomException, MyValidationException;

}
