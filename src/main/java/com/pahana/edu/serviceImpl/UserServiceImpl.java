package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.DuplicateEntryException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.ButtonValues;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createUser(User newUser) throws DuplicateEntryException, SQLException {

		try {

			if (userDao.existByUsername(newUser.getUsername(), newUser.getId())) {
				throw new DuplicateEntryException(
						MessageConstants.USERNAME_EXISTS,
						ButtonPath.MANAGE_USERS,
						ButtonValues.TRY_AGAIN);
			} else {
				userDao.createUser(newUser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateUser(User userLoggedIn, User userToUpdate) throws SQLException, DuplicateEntryException {

		System.out.println(userToUpdate.getUsername());

		boolean modifyingSelf = userLoggedIn.getId().equals(userToUpdate.getId());
		boolean reducingPrivileges = !userLoggedIn.getRole().equals(userToUpdate.getRole());
		boolean deactivatingSelf = !(userLoggedIn.getIsActive() == (userToUpdate.getIsActive()));

		if (modifyingSelf && (reducingPrivileges || deactivatingSelf)) {
			throw new DuplicateEntryException(
					MessageConstants.USER_UPDATE_BY_SELF,
					ButtonPath.MANAGE_USERS,
					ButtonValues.CONTINUE);
		} else if (userDao.existByUsername(userToUpdate.getUsername(), userToUpdate.getId())) {
			throw new DuplicateEntryException(
					MessageConstants.USERNAME_EXISTS,
					ButtonPath.MANAGE_USERS,
					ButtonValues.CONTINUE);
		}

		else {
			userDao.updateUser(userToUpdate);
		}
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		try {
			List<User> usersList = userDao.getAllUsers();
			return usersList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteUser(Long customerId, Long loggedInId) throws SQLException, DuplicateEntryException {
		try {
			if (customerId == loggedInId) {
				throw new DuplicateEntryException(
						MessageConstants.CANNOT_DELETE_BY_SELF,
						ButtonPath.MANAGE_USERS,
						ButtonValues.CONTINUE);
			}
			userDao.deleteUser(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeUsername(Long id, String newUsername) throws DuplicateEntryException {
		try {
			if (userDao.existByUsername(newUsername, id)) {
				throw new DuplicateEntryException(
						MessageConstants.USERNAME_EXISTS,
						ButtonPath.DASHBOARD,
						ButtonValues.TRY_AGAIN);
			} else {
				userDao.changeUsername(id, newUsername);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changePassword(Long loggedInId, String passwordToUpdate) throws SQLException {
		try {
			userDao.changePassword(loggedInId, passwordToUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
