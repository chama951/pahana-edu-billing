package com.pahana.edu.controller;

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

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() {
		// Initialize DAO with a database connection
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");

		PrintWriter printWriter = response.getWriter();

		try {
			User existingUser = userDao.getUserByUsername(username);
			if (existingUser != null) {
				printWriter.print("<h2>Username already exist : " + existingUser.getUsername() + "</h2>");
			}

			UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());

			// Password is automatically hashed in User constructor
			User newUser = new User(username, password, userRole);

			userDao.createUser(newUser);

			printWriter.print("<h2>New User added Successfully : " + newUser.getUsername() + "</h2>");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
