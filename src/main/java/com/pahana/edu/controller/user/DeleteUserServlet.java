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
import com.pahana.edu.utill.database.DBConnectionFactory;

public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public DeleteUserServlet() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		try {
			// Check permissions
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				request.setAttribute("errorMessage", "You don't have permission to manage users");
				request.setAttribute("buttonPath", "/dashboard");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
				return;
			}

			// Check if user is active
			if (!userLoggedIn.getIsActive()) {
				request.setAttribute("errorMessage", "Your account is not active!");
				request.setAttribute("buttonPath", "/login");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
				return;
			}

			// Get user ID to delete
			Long userIdToDelete = Long.valueOf(request.getParameter("userId"));

			// Prevent self-deletion
			if (userLoggedIn.getId().equals(userIdToDelete)) {
				request.setAttribute("errorMessage", "You cannot delete your own account!");
				request.setAttribute("buttonPath", "/user-management");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
				return;
			}

			// Perform deletion
			userDao.deleteUser(userIdToDelete);

			// Success response
			request.setAttribute("successMessage", "User deleted successfully!");
			request.setAttribute("buttonPath", "/user-management");
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
