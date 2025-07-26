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
import com.pahana.edu.utill.database.DBConnectionFactory;

public class ChangeUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public ChangeUsername() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		try {
			request.setAttribute("currentUsername", userLoggedIn.getUsername());
			request.getRequestDispatcher("/views/ChangeUsername.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		try {
			String currentUsername = request.getParameter("currentUsername");
			String newUsername = request.getParameter("newUsername");

			if (userDao.getUserByUsername(newUsername) != null) {
				request.setAttribute("errorMessage", "Username already exists");
				request.setAttribute("currentUsername", currentUsername);
				request.getRequestDispatcher("/views/ChangeUserName.jsp").forward(request, response);
				return;
			}

			userDao.updateUsername(userLoggedIn.getId(), newUsername);

			session.invalidate();
			request.setAttribute("successMessage", "User updated successfully!");
			request.setAttribute("buttonPath", "/login");
			request.setAttribute("buttonValue", "Continue");
			request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

}
