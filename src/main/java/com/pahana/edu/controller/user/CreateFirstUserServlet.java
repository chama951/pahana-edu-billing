package com.pahana.edu.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.service.UserService;
import com.pahana.edu.serviceImpl.UserServiceImpl;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class CreateFirstUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public CreateFirstUserServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String roleParam = request.getParameter("role");
			UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
			User firstUser = new User(
					username,
					password,
					userRole,
					true,
					LocalDateTime.now());

			userService.createUser(firstUser);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.USER_CREATED,
					ButtonPath.LOGIN);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.LOGIN);
		}
	}

}
