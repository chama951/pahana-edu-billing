package com.pahana.edu.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.EndpointValues;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class CreateFirstUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");

		try {
			User userInDb = userDao.getUserByUsername(username);
			if (userInDb != null) {
				ResponseHandler.handleError(request, response,
						MessageConstants.USERNAME_EXISTS,
						EndpointValues.CREATE_USER, ButtonValues.TRY_AGAIN);
			} else {
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
				User newUser = new User(
						username,
						password,
						userRole,
						true,
						LocalDateTime.now());
				userDao.createUser(newUser);

				ResponseHandler.handleSuccess(request, response,
						MessageConstants.FIRST_USER_CREATED,
						EndpointValues.LOGIN, ButtonValues.CONTINUE);
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
