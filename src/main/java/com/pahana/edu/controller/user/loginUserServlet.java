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
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class loginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		// Initialize DAO with a database connection
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to JSP for registration form
		request.getRequestDispatcher("/WEB-INF/views/LoginUser.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			// 1. Validate input
			if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
				request.setAttribute("error", "Username and password are required");
				request.getRequestDispatcher("/WEB-INF/views/LoginUser.jsp").forward(request, response);
				return;
			}

			// 2. Authenticate user
			User userLoggedIn = userDao.getUserByUsername(username);

			if (userLoggedIn == null || !PasswordUtil.checkPassword(password, userLoggedIn.getHashedPassword())) {
				request.setAttribute("error", "Invalid credentials");
				request.getRequestDispatcher("/WEB-INF/views/LoginUser.jsp").forward(request, response);
				return;
			}

			// 3. Create session
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", userLoggedIn);

			// 4. Redirect to home page
			response.sendRedirect("/WEB-INF/views/DashBoard.jsp");

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Database error occurred");
			request.getRequestDispatcher("/WEB-INF/views/LoginUser.jsp").forward(request, response);
		}
	}

}
