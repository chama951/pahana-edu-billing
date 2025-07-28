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
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.EndpointValues;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public DeleteUserServlet() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(request, response,
						MessageConstants.PRIVILEGE_INSUFFICIENT, EndpointValues.DASHBOARD, ButtonValues.BACK);
			}

			Long userIdToDelete = Long.valueOf(request.getParameter("userId"));

			if (userLoggedIn.getId().equals(userIdToDelete)) {
				ResponseHandler.handleError(request, response, MessageConstants.CANNOT_DELETE_BY_SELF,
						EndpointValues.DASHBOARD, ButtonValues.BACK);
			} else {
				userDao.deleteUser(userIdToDelete);

				ResponseHandler.handleSuccess(request, response,
						MessageConstants.USER_DELETED, EndpointValues.DASHBOARD, ButtonValues.CONTINUE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}
}
