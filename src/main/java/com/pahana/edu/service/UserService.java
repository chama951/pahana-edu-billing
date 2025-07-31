package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.User;
import com.pahana.edu.utill.exception.DuplicateEntryException;

public interface UserService {

	List<User> getAllUsers() throws SQLException;

	void updateUser(User userLoggedIn, User userToUpdate) throws SQLException, DuplicateEntryException;

	void createUser(User newUser) throws DuplicateEntryException, SQLException;

	void deleteUser(Long idToDelete, Long loggedInId) throws SQLException, DuplicateEntryException;

	void changePassword(Long loggedInId, String passwordToUpdate) throws SQLException;

	void changeUsername(Long id, String newUsername) throws SQLException, DuplicateEntryException;

}
