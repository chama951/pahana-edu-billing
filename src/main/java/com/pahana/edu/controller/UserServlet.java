package com.pahana.edu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.service.UserService;
import com.pahana.edu.serviceImpl.UserServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public void init() throws ServletException {
		userService = new UserServiceImpl();
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			request.setAttribute("username", userLoggedIn.getUsername());

			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.PRIVILEGE_INSUFFICIENT,
						ButtonPath.DASHBOARD);
				return;
			} else {
				doPost(request, response);
			}
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
					ButtonPath.MANAGE_USERS);
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		try {
			switch (action) {
			case "/create-first-user":
				createFirstUser(request, response);
				break;
			case "/change-password":
				changePassword(request, response);
				break;
			case "/change-username":
				changeUsername(request, response);
				break;
			case "/create-user":
				createUser(request, response);
				break;
			case "/update-user":
				updateUser(request, response);
				break;
			case "/delete-user":
				deleteUser(request, response);
				break;
			default:
				getUsers(request, response);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void createFirstUser(HttpServletRequest request, HttpServletResponse response)
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

	private void changeUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			Long loggedInId = Long.valueOf(userLoggedIn.getId());

			String newUsername = request.getParameter("newUsername");
			userService.changeUsername(loggedInId, newUsername);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.USERNAME_UPDATED,
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

	private void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			Long loggedInId = Long.valueOf(userLoggedIn.getId());

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

	private void getUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		AuthHelper.isUserLoggedIn(request, response);

		try {

			List<User> usersList = userService.getAllUsers();
			request.setAttribute("usersList", usersList);
			request.getRequestDispatcher("/views/ManageUsers.jsp").forward(request, response);
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
					ButtonPath.MANAGE_USERS);
		}

	}

	private void createUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String roleParam = request.getParameter("role");
			UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
			User newUser = new User(
					username,
					password,
					userRole,
					true,
					LocalDateTime.now());

			userService.createUser(newUser);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.USER_CREATED,
					ButtonPath.MANAGE_USERS);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS);
		}
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {

			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");

			Long idToUpdate = Long.valueOf(request.getParameter("id"));
			String roleParam = request.getParameter("role");
			UserRole userRoleEnum = UserRole.valueOf(roleParam.toUpperCase());
			boolean isActiveLoggedIn = Boolean.parseBoolean(request.getParameter("isActive"));

			User userToUpdate = new User(
					idToUpdate,
					userRoleEnum,
					isActiveLoggedIn);

			userService.updateUser(userLoggedIn, userToUpdate);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.USER_UPDATED,
					ButtonPath.MANAGE_USERS);

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
					ButtonPath.MANAGE_USERS);
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {

			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");

			Long loggedInId = Long.valueOf(userLoggedIn.getId());
			Long idToDelete = Long.valueOf(request.getParameter("userId"));

			userService.deleteUser(idToDelete, loggedInId);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.USER_DELETED,
					ButtonPath.MANAGE_USERS);

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
					ButtonPath.MANAGE_USERS);
		}

	}

}
