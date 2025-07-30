package com.pahana.edu.controller.customer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.model.Customer;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;
import com.pahana.edu.utill.database.DBConnectionFactory;

public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDao customerDao;

	@Override
	public void init() throws ServletException {
		customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

		request.getRequestDispatcher("/views/CreateCustomer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		try {
			Long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
			Customer customerInDb = customerDao.getUserByAccNo(accountNumber);
			if (customerInDb != null) {
				ResponseHandler.handleError(request, response, MessageConstants.CUSTOMER_NUMBER_EXISTS,
						ButtonPath.CREATE_CUSTOMER, ButtonValues.TRY_AGAIN);
			} else {
				String firstName = request.getParameter("firstName");
				String lastname = request.getParameter("lastName");
				String address = request.getParameter("address");
				String phoneNumber = request.getParameter("phoneNumber");
				String email = request.getParameter("email");
				Integer unitsConsumed = Integer.valueOf(request.getParameter("unitsConsumed"));
				Customer newCustomer = new Customer(
						accountNumber,
						firstName,
						lastname,
						address,
						phoneNumber,
						email,
						unitsConsumed);
				customerDao.createCustomer(newCustomer);

				ResponseHandler.handleSuccess(request, response, MessageConstants.CUSTOMER_CREATED,
						ButtonPath.GET_CUSTOMERS, ButtonValues.CONTINUE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

}
