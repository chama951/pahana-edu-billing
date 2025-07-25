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
import com.pahana.edu.utill.database.DBConnectionFactory;

public class createUserServlet extends HttpServlet {
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
		request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");

		LocalDateTime now = LocalDateTime.now();

		try {
			User user = userDao.getUserByUsername(username);
			if (user != null) {
				request.setAttribute("error", "Username already exist!");
				request.setAttribute("errorMessage", "Username already exist!");
				// passing the next page as argument
				request.setAttribute("buttonPath", "/create-user");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else {
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
				User newUser = new User(
						username,
						password,
						userRole,
						true,
						now);
				userDao.createUser(newUser);
				request.setAttribute("successMessage", "User created successfully!");
				// passing the next page as argument
				request.setAttribute("buttonPath", "/login");
				request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
//				response.sendRedirect(request.getContextPath() + "/login");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/views/CreateUser.jsp").forward(request, response);
		}

	}
}
