package com.pahana.edu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.Customer;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.serviceImpl.CustomerServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.exception.DuplicateEntryException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.ButtonValues;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService customerService;

	@Override
	public void init() throws ServletException {
		customerService = new CustomerServiceImpl();
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		AuthHelper.isUserLoggedIn(request, response);
		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.PRIVILEGE_INSUFFICIENT,
						ButtonPath.DASHBOARD,
						ButtonValues.BACK);
			} else {
				doPost(request, response);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		try {
			switch (action) {
			case "/create-customer":
				createCustomer(request, response);
				break;
			case "/update-customer":
				updateCustomer(request, response);
				break;
			case "/delete-customer":
				deleteCustomer(request, response);
				break;
			default:
				getCustomers(request, response);
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void getCustomers(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			List<Customer> customerList = customerService.getAllCustomers();
			request.setAttribute("customerList", customerList);
			request.getRequestDispatcher("/views/ManageCustomers.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_USERS,
					ButtonValues.TRY_AGAIN);
		}

	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			Long customerId = Long.valueOf(request.getParameter("id"));

			customerService.deleteCustomer(customerId);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CUSTOMER_DELETED,
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.CONTINUE);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.TRY_AGAIN);
		}

	}

	private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, DuplicateEntryException {

		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
			String email = request.getParameter("email");
			String firstName = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String address = request.getParameter("address");
			String phoneNumber = request.getParameter("phoneNumber");
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

			customerService.updateCustomer(customerToUpdate);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CUSTOMER_UPDATED,
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.CONTINUE);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (DuplicateEntryException e) {
			// Handle known duplicate cases
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					e.getRedirectPath(),
					e.getButtonLabel());

		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.TRY_AGAIN);
		}

	}

	private void createCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			Long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			String firstName = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String address = request.getParameter("address");
			Integer unitsConsumed = Integer.valueOf(request.getParameter("unitsConsumed"));
			Customer newCustomer = new Customer(
					accountNumber,
					firstName,
					lastname,
					address,
					phoneNumber,
					email,
					unitsConsumed);

			customerService.createCustomer(newCustomer);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CUSTOMER_CREATED,
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.CONTINUE);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (DuplicateEntryException e) {
			// Handle known duplicate cases
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					e.getRedirectPath(),
					e.getButtonLabel());

		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS,
					ButtonValues.TRY_AGAIN);
		}

	}

}
