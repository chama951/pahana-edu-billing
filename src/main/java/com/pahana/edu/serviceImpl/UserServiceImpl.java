package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.PahanaEduException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public User createUser(User newUser) throws PahanaEduException, SQLException {

		try {
			checkUsernameExist(newUser);
			userDao.createUser(newUser);
			return newUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User userLoggedIn, User userToUpdate) throws SQLException, PahanaEduException {

		boolean modifyingSelf = userLoggedIn.getId().equals(userToUpdate.getId());
		boolean reducingPrivileges = !userLoggedIn.getRole().equals(userToUpdate.getRole());
		boolean deactivatingSelf = !(userLoggedIn.getIsActive() == (userToUpdate.getIsActive()));

		if (modifyingSelf && (reducingPrivileges || deactivatingSelf)) {
			throw new PahanaEduException(
					MessageConstants.USER_UPDATE_BY_SELF,
					ButtonPath.MANAGE_USERS);
		}

		checkUsernameExist(userToUpdate);
		userDao.updateUser(userToUpdate);

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
	public void deleteUser(Long customerId, Long loggedInId) throws SQLException, PahanaEduException {
		try {
			if (customerId == loggedInId) {
				throw new PahanaEduException(
						MessageConstants.CANNOT_DELETE_BY_SELF,
						ButtonPath.MANAGE_USERS);
			}
			userDao.deleteUser(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeUsername(Long id, String newUsername) throws PahanaEduException, SQLException {
		try {
			User user = new User(id, newUsername);
			checkUsernameExist(user);
			userDao.changeUsername(id, newUsername);
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

	private void checkUsernameExist(User newUser) throws PahanaEduException {
		try {
			if (userDao.existByUsername(newUser.getUsername(), newUser.getId())) {
				throw new PahanaEduException(
						MessageConstants.USERNAME_EXISTS,
						ButtonPath.MANAGE_USERS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
