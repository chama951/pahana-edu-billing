package com.pahana.edu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.service.BillService;
import com.pahana.edu.serviceImpl.BillServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class InvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BillService billService;

	public void init() throws ServletException {
		billService = new BillServiceImpl();
		super.init();
	}

	public InvoiceServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.VIEW_BILLS)
					&& !userLoggedIn.getRole().hasPrivilege(Privilege.CANCEL_BILLS)) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();

		try {
			switch (action) {
			case "/update-invoice":
				updateInvoice(request, response);
			case "/get-billitems":
				getBillItemList(request, response);
			default:
				getInvoices(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private void getBillItemList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		Long billId = Long.valueOf(request.getParameter("invoiceId"));
		try {
			List<BillItem> billItems = billService.getBillItemList(billId);
			Bill bill = billService.getBillById(billId);
			request.setAttribute("bill", bill);
			request.setAttribute("billItems", billItems);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_INVOICES);
		}
	}

	private void getInvoices(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			List<Bill> billList = billService.getAllBills();
			request.setAttribute("billList", billList);
			request.getRequestDispatcher("/views/ManageInvoice.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_INVOICES);
		}
	}

	private void updateInvoice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			Long billId = Long.valueOf(request.getParameter("invoiceId"));

			String statusParam = request.getParameter("status");
			BillStatus billStatus = BillStatus.valueOf(statusParam.toUpperCase());

			billService.changeStatus(billId, billStatus);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.BILL_STATUS_CHANGED,
					ButtonPath.MANAGE_INVOICES);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_INVOICES);
		}
	}

}
