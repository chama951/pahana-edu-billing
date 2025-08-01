package com.pahana.edu.controller.user.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.User;
import com.pahana.edu.service.UserService;
import com.pahana.edu.serviceImpl.UserServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public void init() throws ServletException {
		userService = new UserServiceImpl();
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		response.sendRedirect(request.getContextPath() + "/views/ChangePassword.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");
		Long loggedInId = Long.valueOf(userLoggedIn.getId());
		AuthHelper.isUserLoggedIn(request, response);

		try {

			String currentPassword = request.getParameter("currentPassword");
			String newPassword = request.getParameter("newPassword");

			if (!PasswordUtil.checkPassword(currentPassword, userLoggedIn.getHashedPassword())) {
				ResponseHandler.handleError(request, response,
						MessageConstants.INCORRECT_CURRENT_PASSWORD, ButtonPath.CHANGE_PASSWORD);
				return;
			}
			String passwordToUpdate = PasswordUtil.hashPassword(newPassword);

			userService.changePassword(loggedInId, passwordToUpdate);

			session.invalidate();

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.PASSWORD_UPDATED,
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
					ButtonPath.DASHBOARD);
		}

	}
}
