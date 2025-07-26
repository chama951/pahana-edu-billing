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

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public UpdateUserServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");
		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				request.setAttribute("errorMessege", "You don't have permission to manage users");
				request.setAttribute("buttonPath", "/dashboard");// must change to the dashdoard in the future
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else if (!userLoggedIn.getIsActive()) {
				request.setAttribute("errorMessage", "User is not active!");
				// passing the next page as argument
				request.setAttribute("buttonPath", "/login");
				request.setAttribute("buttonValue", "Go Back");
				request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
			} else {
				Long idToUpdate = Long.valueOf(request.getParameter("id"));
				User userToUpdate = userDao.getUserById(idToUpdate);

				String roleParam = request.getParameter("role");
				UserRole userRole = UserRole.valueOf(roleParam.toUpperCase());
				boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

				boolean modifyingSelf = userLoggedIn.getId().equals(idToUpdate);
				boolean reducingPrivileges = !userRole.hasPrivilege(Privilege.MANAGE_USERS);
				boolean deactivatingSelf = modifyingSelf && !isActive && userLoggedIn.getIsActive();

				userToUpdate.setRole(userRole);
				userToUpdate.setIsActive(isActive);
				userDao.updateUserByAdmin(userToUpdate);

				if (modifyingSelf && (reducingPrivileges || deactivatingSelf)) {
					session.invalidate();
					request.setAttribute("successMessage", "User updated successfully!");
					request.setAttribute("buttonPath", "/login");
					request.setAttribute("buttonValue", "Continue");
					request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
				} else {
					request.setAttribute("successMessage", "User updated successfully!");
					request.setAttribute("buttonPath", "/user-management");
					request.setAttribute("buttonValue", "Continue");
					request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

}
