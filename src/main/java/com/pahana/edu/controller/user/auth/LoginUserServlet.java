package com.pahana.edu.controller.user.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.ButtonValues;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			User userInDb = userDao.getUserByUsername(username);
			if (userInDb == null) {
				ResponseHandler.handleError(request, response, MessageConstants.USER_NOT_FOUND, ButtonPath.LOGIN,
						ButtonValues.BACK);
			} else if (!PasswordUtil.checkPassword(password, userInDb.getHashedPassword())) {
				ResponseHandler.handleError(request, response, MessageConstants.INVALID_PASSWORD, ButtonPath.LOGIN,
						ButtonValues.TRY_AGAIN);
			} else if (!userInDb.getIsActive()) {
				ResponseHandler.handleError(request, response, MessageConstants.ACCOUNT_DEACTIVATED,
						ButtonPath.LOGIN, ButtonValues.BACK);
			} else {
				LocalDateTime lastLoginTime = LocalDateTime.now();
				userDao.updateLastLogin(userInDb.getId(), lastLoginTime);
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", userInDb);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
		ResponseHandler.handleSuccess(request, response,
				MessageConstants.LOGIN_SUCCESS + username, ButtonPath.DASHBOARD,
				ButtonValues.DASHBNOARD);
	}

}
