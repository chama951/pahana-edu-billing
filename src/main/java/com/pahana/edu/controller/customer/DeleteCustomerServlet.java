package com.pahana.edu.controller.customer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class DeleteCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDao customerDao;

	@Override
	public void init() throws ServletException {
		customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	public DeleteCustomerServlet() {
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
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(request, response,
						MessageConstants.PRIVILEGE_INSUFFICIENT, ButtonPath.DASHBOARD, ButtonValues.BACK);
			} else {
				Long customerId = Long.valueOf(request.getParameter("id"));
				customerDao.deleteCustomer(customerId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
		ResponseHandler.handleSuccess(request, response,
				MessageConstants.CUSTOMER_DELETED, ButtonPath.MANAGE_CUSTOMERS, ButtonValues.CONTINUE);
	}
}