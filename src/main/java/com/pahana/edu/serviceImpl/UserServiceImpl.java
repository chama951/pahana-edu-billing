package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.Validator;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.exception.MyValidationException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public User createUser(User newUser) throws MyCustomException, SQLException, MyValidationException {

		try {
			Validator.validUser(newUser);
			checkUsernameExist(newUser);

			String hashedPassword = PasswordUtil.hashPassword(newUser.getHashedPassword());
			newUser.setHashedPassword(hashedPassword);

			User userInDb = userDao.createUser(newUser);
			return userInDb;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User userLoggedIn, User userToUpdate)
			throws SQLException, MyCustomException, MyValidationException {

		boolean modifyingSelf = userLoggedIn.getId().equals(userToUpdate.getId());
		boolean reducingPrivileges = !userLoggedIn.getRole().equals(userToUpdate.getRole());
		boolean deactivatingSelf = !(userLoggedIn.getIsActive() == (userToUpdate.getIsActive()));

		if (modifyingSelf && (reducingPrivileges || deactivatingSelf)) {
			throw new MyCustomException(
					MessageConstants.USER_UPDATE_BY_SELF,
					ButtonPath.MANAGE_USERS);
		}

		checkUsernameExist(userToUpdate);

		Validator.validUser(userToUpdate);

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
	public void deleteUser(Long customerId, Long loggedInId) throws SQLException, MyCustomException {
		try {
			if (customerId == loggedInId) {
				throw new MyCustomException(
						MessageConstants.CANNOT_DELETE_BY_SELF,
						ButtonPath.MANAGE_USERS);
			}
			userDao.deleteUser(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeUsername(Long id, String newUsername)
			throws MyCustomException, SQLException, MyValidationException {
		try {
			User userInDb = userDao.getUserById(id);
			userInDb.setUsername(newUsername);

			Validator.validUser(userInDb);

			checkUsernameExist(userInDb);

			userDao.updateUser(userInDb);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void changePassword(Long loggedInId, String passwordToUpdate) throws SQLException, MyValidationException {
		try {
			User userInDb = userDao.getUserById(loggedInId);
			userInDb.setHashedPassword(passwordToUpdate);

			Validator.validUser(userInDb);

			String hashedPassword = PasswordUtil.hashPassword(passwordToUpdate);

			userDao.changePassword(loggedInId, hashedPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void checkUsernameExist(User newUser) throws MyCustomException {
		try {
			if (userDao.existByUsername(newUser.getUsername(), newUser.getId())) {
				throw new MyCustomException(
						MessageConstants.USERNAME_EXISTS,
						ButtonPath.MANAGE_USERS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
