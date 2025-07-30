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
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class UpdateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDao customerDao;

	@Override
	public void init() throws ServletException {
		customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		AuthHelper.isUserLoggedIn(request, response);

		try {

			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(request, response,
						MessageConstants.PRIVILEGE_INSUFFICIENT, ButtonPath.DASHBOARD, ButtonValues.BACK);
			}
			Long customerId = Long.parseLong(request.getParameter("id"));

			Long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
			boolean customerByAccNo = customerDao.checkCustomerByIdAndAccNo(accountNumber, customerId);
			String email = request.getParameter("email");
			boolean customerByemail = customerDao.checkCustomerByIdAndEmail(email, customerId);
			String phoneNumber = request.getParameter("phoneNumber");
			boolean customerByPhoneNo = customerDao.checkCustomerByIdAndPhoneNo(phoneNumber, customerId);

			if (customerByAccNo) {
				ResponseHandler.handleError(request, response, MessageConstants.CUSTOMER_NUMBER_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS, ButtonValues.TRY_AGAIN);
				return;
			} else if (customerByemail) {
				ResponseHandler.handleError(request, response, MessageConstants.CUSTOMER_EMAIL_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS, ButtonValues.TRY_AGAIN);

			} else if (customerByPhoneNo) {
				ResponseHandler.handleError(request, response, MessageConstants.CUSTOMER_PHONENO_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS, ButtonValues.TRY_AGAIN);
			} else {
				Long id = Long.parseLong(request.getParameter("id"));
				String firstName = request.getParameter("firstName");
				String lastname = request.getParameter("lastName");
				String address = request.getParameter("address");
				Integer unitsConsumed = Integer.valueOf(request.getParameter("unitsConsumed"));
				Customer customerToUpdate = new Customer(
						id,
						accountNumber,
						firstName,
						lastname,
						address,
						phoneNumber,
						email,
						unitsConsumed);
				customerDao.updateCustomer(customerToUpdate);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
		ResponseHandler.handleSuccess(request, response, MessageConstants.CUSTOMER_UPDATED,
				ButtonPath.MANAGE_CUSTOMERS, ButtonValues.CONTINUE);
	}

}
