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
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.model.enums.UserRole;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class updateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public updateUserServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)
					|| !userLoggedIn.getRole().hasPrivilege(Privilege.ALL)) {
				request.setAttribute("accessDeniedMessege", "You don't have permission to access User Management");
			} else {
				Long idToUpdate = Long.valueOf(request.getParameter("id"));
				User userToUpdate = userDao.getUserById(idToUpdate);

				String roleParam = request.getParameter("role");
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
				boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

				userToUpdate.setRole(userRole);
				userToUpdate.setIsActive(isActive);
				userDao.updateUserByAdmin(userToUpdate);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
