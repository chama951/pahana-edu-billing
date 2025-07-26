package com.pahana.edu.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class DisplayUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public DisplayUsers() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");
		request.setAttribute("username", userLoggedIn.getUsername());
		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				request.setAttribute("errorMessage", "You don't have permission to access User Management");
				request.setAttribute("buttonPath", "/dashboard");// change in the dashboard
				request.setAttribute("buttonValue", "Go back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else {
				List<User> usersList = userDao.getAllUsers();
				request.setAttribute("usersList", usersList);
				request.getRequestDispatcher("/views/DisplayUsers.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
