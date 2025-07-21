package com.pahana.edu.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
		// Initialize DAO with a database connection
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to JSP for registration form
		request.getRequestDispatcher("/WEB-INF/views/RegisterNewUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");

		PrintWriter printWriter = response.getWriter();

		try {
			User existingUser = userDao.getUserByUsername(username);
			if (existingUser != null) {
//				printWriter.print("<h2>Username already exist : " + existingUser.getUsername() + "</h2>");
				request.setAttribute("error", "Username already exist");
				request.getRequestDispatcher("/WEB-INF/views/RegisterNewUser.jsp").forward(request, response);
			} else {
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());

				// Password is automatically hashed in User constructor
				User newUser = new User(username, password, userRole);

				userDao.createUser(newUser);

				printWriter.print("<h2>New User added Successfully : " + newUser.getUsername() + "</h2>");
				request.getRequestDispatcher("/WEB-INF/views/LoginUser.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
