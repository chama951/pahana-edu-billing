package com.pahana.edu.controller.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public UpdateUserServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		AuthHelper.isUserLoggedIn(request, response);

		try {

			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(request, response,
						MessageConstants.PRIVILEGE_INSUFFICIENT, ButtonPath.DASHBOARD, ButtonValues.BACK);
			} else {
				Long idToUpdate = Long.valueOf(request.getParameter("id"));
				User userToUpdate = userDao.getUserById(idToUpdate);

				String roleParam = request.getParameter("role");
				UserRole loggedInuserRole = UserRole.valueOf(roleParam.toUpperCase());
				boolean isActiveLoggedIn = Boolean.parseBoolean(request.getParameter("isActive"));

				boolean modifyingSelf = userLoggedIn.getId().equals(idToUpdate);
				boolean reducingPrivileges = !loggedInuserRole.equals(userToUpdate.getRole());
				boolean deactivatingSelf = !(isActiveLoggedIn == (userToUpdate.getIsActive()));
				;

				if (modifyingSelf && (reducingPrivileges || deactivatingSelf)) {
					ResponseHandler.handleError(request, response,
							MessageConstants.USER_UPDATE_BY_SELF, ButtonPath.GET_USERS, ButtonValues.BACK);
				} else {
					userToUpdate.setRole(loggedInuserRole);
					userToUpdate.setIsActive(isActiveLoggedIn);
					userDao.updateUser(userToUpdate);
					ResponseHandler.handleSuccess(request, response,
							MessageConstants.USER_UPDATED, ButtonPath.GET_USERS, ButtonValues.CONTINUE);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

}
