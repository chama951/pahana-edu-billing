package com.pahana.edu.controller.dashboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UserDao userDao;

	public DashboardServlet() {
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

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");

        request.setAttribute("loggedInUsername", currentUser.getUsername());
        request.setAttribute("userRole", currentUser.getRole());

		request.getRequestDispatcher("/views/Dashboard.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
