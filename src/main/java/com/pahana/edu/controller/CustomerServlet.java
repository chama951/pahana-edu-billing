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
import com.pahana.edu.utill.exception.PahanaEduException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService customerService;

	public void init() throws ServletException {
		customerService = new CustomerServiceImpl();
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_CUSTOMERS)) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.PRIVILEGE_INSUFFICIENT,
						ButtonPath.DASHBOARD);
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
			case "/select-customer":
				selectCustomer(request, response);
			default:
				getCustomers(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private void selectCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

		try {
			Long customerId = Long.parseLong(request.getParameter("customerId"));

			Customer selectedCustomer = customerService.getCustomerById(customerId);

			request.getSession().setAttribute("selectedCustomer", selectedCustomer);
			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CUSTOMER_SELECTED,
					ButtonPath.MANAGE_CUSTOMERS);
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS);
		}
	}

	private void getCustomers(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AuthHelper.isUserLoggedIn(request, response);

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
					ButtonPath.MANAGE_CUSTOMERS);
		}

	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AuthHelper.isUserLoggedIn(request, response);

		try {
			Long customerId = Long.valueOf(request.getParameter("id"));

			customerService.deleteCustomer(customerId);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CUSTOMER_DELETED,
					ButtonPath.MANAGE_CUSTOMERS);

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
					ButtonPath.MANAGE_CUSTOMERS);
		}

	}

	private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, PahanaEduException {

		AuthHelper.isUserLoggedIn(request, response);

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
					ButtonPath.MANAGE_CUSTOMERS);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (PahanaEduException e) {
			// Handle known duplicate cases
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					e.getRedirectPath());

		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS);
		}

	}

	private void createCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AuthHelper.isUserLoggedIn(request, response);

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
					ButtonPath.MANAGE_CUSTOMERS);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (PahanaEduException e) {
			// Handle known duplicate cases
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					e.getRedirectPath());

		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_CUSTOMERS);
		}

	}

}
