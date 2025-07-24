package com.pahana.edu.controller.user.auth;

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
			User user = userDao.getUserByUsername(username);
			if (user == null || !PasswordUtil.checkPassword(password, user.getHashedPassword())) {
				// set error to the jsp
				request.setAttribute("error", "Invalid credentials");
				// calling jsp and show the error to the client
				request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
			} else if (!user.getIsActive()) {
				request.setAttribute("error", "User is not active");
				request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
			} else {
				userDao.updateLastLogin(user.getId());
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", user);
				// redirect to the dashboard by accessing the DashboardServlet
				response.sendRedirect(request.getContextPath() + "/dashboard");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/views/LoginUser.jsp").forward(request, response);
		}
	}

}
