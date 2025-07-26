package com.pahana.edu.controller.user.auth;

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
import com.pahana.edu.utill.PasswordUtil;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class LoginUserServlet extends HttpServlet {
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
		request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			User userInDb = userDao.getUserByUsername(username);
			if (userInDb == null) {
				request.setAttribute("errorMessage", "User not found");
				request.setAttribute("buttonPath", "/login");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else if (userInDb == null || !PasswordUtil.checkPassword(password, userInDb.getHashedPassword())) {
				request.setAttribute("errorMessage", "Invalid credentials!");
				request.setAttribute("buttonPath", "/login");
				request.setAttribute("buttonValue", "Try Again");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else if (!userInDb.getIsActive()) {
				request.setAttribute("errorMessage", "User is not active!");
				request.setAttribute("buttonPath", "/login");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else {
				LocalDateTime lastLoginTime = LocalDateTime.now();
				userDao.updateLastLogin(userInDb.getId(), lastLoginTime);
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", userInDb);
//				response.sendRedirect(request.getContextPath() + "/get-user-list");
				request.getRequestDispatcher("/dashboard").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
		}catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

}
