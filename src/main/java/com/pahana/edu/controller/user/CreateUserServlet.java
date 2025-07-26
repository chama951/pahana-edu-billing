package com.pahana.edu.controller.user;

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
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class CreateUserServlet extends HttpServlet {
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
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");
		if (userLoggedIn != null) {
			try {
				request.setAttribute("currentUsername", userLoggedIn.getUsername());
				request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");

		try {
			User userInDb = userDao.getUserByUsername(username);
			if (userInDb != null) {
				request.setAttribute("errorMessage", "Username already exist!");
				request.setAttribute("buttonPath", "/create-user");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else {
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
				User newUser = new User(
						username,
						password,
						userRole,
						true,
						LocalDateTime.now());
				userDao.createUser(newUser);

				HttpSession session = request.getSession();
				User userLoggedIn = (User) session.getAttribute("currentUser");

				if (!userLoggedIn.equals(null)) {
					request.setAttribute("successMessage", "User created successfully!");
					request.setAttribute("buttonPath", "/user-management");
					request.setAttribute("buttonValue", "Continue");
					request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
				} else {
					request.setAttribute("successMessage", "User created successfully!");
					request.setAttribute("buttonPath", "/login");
					request.setAttribute("buttonValue", "Login");
					request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}

	}
}
