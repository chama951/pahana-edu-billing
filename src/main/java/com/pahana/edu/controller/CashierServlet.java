package com.pahana.edu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.service.BillService;
import com.pahana.edu.serviceImpl.BillServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class CashierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public BillService billService;

	@Override
	public void init() throws ServletException {
		billService = new BillServiceImpl();
		super.init();
	}

	public CashierServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			Customer selectedCustomer = (Customer) session.getAttribute("selectedCustomer");
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.CREATE_BILLS)) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.PRIVILEGE_INSUFFICIENT,
						ButtonPath.DASHBOARD);
			} else if (selectedCustomer == null) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.PLEASE_SELECT_A_CUSTOMER,
						ButtonPath.DASHBOARD);
			} else {
				doPost(request, response);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		try {
			switch (action) {
			case "/clear-cart":
				clearCart(request, response);
				break;
			case "/cart-item-remove":
				cartItemRemove(request, response);
				break;
			case "/create-bill":
				createBill(request, response);
				break;
			default:
				getCashier(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void createBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("currentUser");
			Customer selectedCustomer = (Customer) session.getAttribute("selectedCustomer");

			Long userId = currentUser.getId();
			Long customerId = selectedCustomer.getId();

			String billStatusStr = request.getParameter("billStatus");
			BillStatus billStatus = BillStatus.valueOf(billStatusStr);

			List<Item> itemList = (List<Item>) session.getAttribute("itemList");

			billService.createBill(itemList, customerId, userId, billStatus);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.BILL_CREATED_SUCCESSFULLY,
					ButtonPath.DASHBOARD);

			List<Item> newList = new ArrayList<>();
			request.getSession().setAttribute("itemList", newList);

			request.getSession().setAttribute("selectedCustomer", null);

		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.CASHIER);
		}
	}

	private void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			List<Item> newList = new ArrayList<>();
			request.getSession().setAttribute("itemList", newList);
			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.CART_CLEARED,
					ButtonPath.CASHIER);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.CASHIER);
		}

	}

	@SuppressWarnings("unchecked")
	private void cartItemRemove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			String itemId = request.getParameter("id");
			HttpSession session = request.getSession();
			List<Item> itemList = (List<Item>) session.getAttribute("itemList");

			if (itemList != null && itemId != null) {
				List<Item> newList = new ArrayList<>();
				for (Item item : itemList) {
					if (!item.getId().toString().equals(itemId)) {
						newList.add(item);
					}
				}

				session.setAttribute("itemList", newList);

				ResponseHandler.handleSuccess(
						request,
						response,
						MessageConstants.ITEM_REMOVED_CART,
						ButtonPath.CASHIER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.CASHIER);
		}

	}

	private void getCashier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {

			request.getRequestDispatcher("/views/Cashier.jsp").forward(request, response);

		} catch (

		NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.DASHBOARD);
		}

	}

}
