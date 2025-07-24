package com.pahana.edu.controller.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
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

public class dashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UserDao userDao;

	public dashboardServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		HttpSession session = request.getSession();
		try {
			User user = (User) session.getAttribute("currentUser");
			printWriter.print("<h2> Logged in username : " + user.getUsername() + "</h>");
			printWriter.print("<h2> Logged in password : " + user.getHashedPassword() + "</h>");
			printWriter.print("<h2> Logged in userRole : " + user.getRole() + "</h>");
			if (user.getUsername() != userDao.getUserByUsername(user.getUsername()).getUsername()) {
				// Just added for compile the sqlexception without errors
			}
//			request.getRequestDispatcher("/views/Dashboard.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
