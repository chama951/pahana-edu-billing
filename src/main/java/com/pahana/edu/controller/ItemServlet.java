package com.pahana.edu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.Privilege;
import com.pahana.edu.service.ItemService;
import com.pahana.edu.serviceImpl.ItemServiceImpl;
import com.pahana.edu.utill.AuthHelper;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;
import com.pahana.edu.utill.responseHandling.ResponseHandler;

public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemService itemService;

	@Override
	public void init() throws ServletException {
		itemService = new ItemServiceImpl();
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		try {
			HttpSession session = request.getSession();
			User userLoggedIn = (User) session.getAttribute("currentUser");
			if (!userLoggedIn.getRole().hasPrivilege(Privilege.MANAGE_ITEMS)) {
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
			case "/create-item":
				createItem(request, response);
				break;
			case "/update-item":
				updateItem(request, response);
				break;
			case "/delete-item":
				deleteItem(request, response);
				break;
			case "/add-to-cart":
				addToCart(request, response);
			default:
				getItems(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);
		try {
			Long itemId = Long.parseLong(request.getParameter("itemId"));
			Integer qtyToCart = Integer.valueOf(request.getParameter("quantity"));

			Item selectedItem = itemService.getItemById(itemId);

			if (selectedItem.getQuantityInStock() < qtyToCart) {
				ResponseHandler.handleError(
						request,
						response,
						MessageConstants.QUANTITY_NOT_ENOUGH_IN_STOCK,
						ButtonPath.MANAGE_ITEMS);
			} else {
				selectedItem.setQuantityInStock(qtyToCart);

				List<Item> itemList = (List<Item>) request.getSession().getAttribute("itemList");

				if (itemList == null) {
					itemList = new ArrayList<Item>();
				}

				boolean itemExists = false;

				if (selectedItem != null) {
					for (Item item : itemList) {
						if (item.getId().equals(selectedItem.getId())) {
							item.setQuantityInStock(item.getQuantityInStock() + qtyToCart);
							itemExists = true;
							break;
						}
					}
					if (!itemExists) {
						itemList.add(selectedItem);
					}
				}

				request.getSession().setAttribute("itemList", itemList);
				ResponseHandler.handleSuccess(
						request,
						response,
						MessageConstants.ITEM_ADDED_TO_THE_CART,
						ButtonPath.MANAGE_ITEMS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ResponseHandler.handleError(
					request,
					response,
					e.getMessage(),
					ButtonPath.MANAGE_ITEMS);
		}
	}

	private void getItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		try {
			List<Item> itemList = itemService.getAllItems();
			request.setAttribute("itemList", itemList);
			request.getRequestDispatcher("/views/ManageItems.jsp").forward(request, response);
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
					ButtonPath.MANAGE_ITEMS);
		}
	}

	private void deleteItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthHelper.isUserLoggedIn(request, response);

		try {
			Long itemId = Long.valueOf(request.getParameter("id"));

			itemService.deleteItem(itemId);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.ITEM_DELETED,
					ButtonPath.MANAGE_ITEMS);

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
					ButtonPath.MANAGE_ITEMS);
		}
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		try {
			Long id = Long.valueOf(request.getParameter("id"));
			String title = request.getParameter("title");
			String isbn = request.getParameter("isbn");
			double price = Double.valueOf(request.getParameter("price"));
			double discountPercentage = Double.valueOf(request.getParameter("discountPercentage"));
			Integer quantityInStock = Integer.valueOf(request.getParameter("quantityInStock"));
			User user = currentUser;
			String description = request.getParameter("description");
			String author = request.getParameter("author");
			Integer publicationYear = Integer.valueOf(request.getParameter("publicationYear"));
			String publisher = request.getParameter("publisher");
			Item itemToUpdate = new Item(
					id,
					title,
					isbn,
					price,
					quantityInStock,
					user,
					description,
					author,
					publicationYear,
					publisher,
					discountPercentage);

			itemService.updateItem(itemToUpdate);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.ITEM_UPDATED,
					ButtonPath.MANAGE_ITEMS);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (MyCustomException e) {
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
					ButtonPath.MANAGE_ITEMS);
		}

	}

	private void createItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthHelper.isUserLoggedIn(request, response);

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");

		try {
			String title = request.getParameter("title");
			String isbn = request.getParameter("isbn");
			double price = Double.valueOf(request.getParameter("price"));
			double discountPercentage = Double.valueOf(request.getParameter("discountPercentage"));
			System.out.println("discountPercentage/create : " + discountPercentage);
			Integer quantityInStock = Integer.valueOf(request.getParameter("quantityInStock"));
			String description = request.getParameter("description");
			String author = request.getParameter("author");
			Integer publicationYear = Integer.valueOf(request.getParameter("publicationYear"));
			String publisher = request.getParameter("publisher");
			Item newItem = new Item(
					title,
					isbn,
					price,
					quantityInStock,
					currentUser,
					description,
					author,
					publicationYear,
					publisher,
					discountPercentage);

			itemService.createItem(newItem);

			ResponseHandler.handleSuccess(
					request,
					response,
					MessageConstants.ITEM_CREATED,
					ButtonPath.MANAGE_ITEMS);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		} catch (MyCustomException e) {
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
					ButtonPath.MANAGE_ITEMS);
		}

	}

}
