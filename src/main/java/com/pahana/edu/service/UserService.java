package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.User;
import com.pahana.edu.utill.exception.PahanaEduException;

public interface UserService {

	List<User> getAllUsers() throws SQLException;

	void updateUser(User userLoggedIn, User userToUpdate) throws SQLException, PahanaEduException;

	void createUser(User newUser) throws PahanaEduException, SQLException;

	void deleteUser(Long idToDelete, Long loggedInId) throws SQLException, PahanaEduException;

	void changePassword(Long loggedInId, String passwordToUpdate) throws SQLException;

	void changeUsername(Long id, String newUsername) throws SQLException, PahanaEduException;

}
