package com.pahana.edu.controller.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

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

public class GetCustomersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDao customerDao;

	public GetCustomersServlet() {
		super();
		customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");

		AuthHelper.isUserLoggedIn(request, response);

		try {
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_USERS)) {
				ResponseHandler.handleError(request, response,
						MessageConstants.PRIVILEGE_INSUFFICIENT, ButtonPath.DASHBOARD, ButtonValues.BACK);
			} else {
				List<Customer> customerList = customerDao.getAllCustomers();
				request.setAttribute("customerList", customerList);
				PrintWriter out = response.getWriter();
				for (Customer customer : customerList) {
					out.print("<h1>" + customer.getFirstName() + "</h1>");
					out.print("<h1>" + customer.getLastName() + "</h1>");
				}
//				request.getRequestDispatcher("/views/ManageCustomers.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
