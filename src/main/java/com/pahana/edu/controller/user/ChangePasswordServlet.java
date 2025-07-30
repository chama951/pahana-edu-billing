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
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

		request.getRequestDispatcher("/views/ChangePassword.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		AuthHelper.isUserLoggedIn(request, response);

		try {

			String currentPassword = request.getParameter("currentPassword");
			String newPassword = request.getParameter("newPassword");
			if (!PasswordUtil.checkPassword(currentPassword, userLoggedIn.getHashedPassword())) {
				ResponseHandler.handleError(request, response,
						MessageConstants.INCORRECT_CURRENT_PASSWORD, ButtonPath.CHANGE_PASSWORD,
						ButtonValues.TRY_AGAIN);
				return;
			}
			userDao.updatePassword(userLoggedIn.getId(), PasswordUtil.hashPassword(newPassword));
			session.invalidate();
			ResponseHandler.handleSuccess(request, response,
					MessageConstants.PASSWORD_UPDATED, ButtonPath.LOGIN, ButtonValues.CONTINUE);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}
}
