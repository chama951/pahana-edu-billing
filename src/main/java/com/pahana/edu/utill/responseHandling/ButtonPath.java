package com.pahana.edu.utill.responseHandling;

public final class ButtonPath {
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String DASHBOARD = "/views/Dashboard.jsp";
	public static final String CREATE_USER = "/user/create-user";
	public static final String UPDATE_USER = "/user/update-user";
	public static final String DELETE_USER = "/user/deleteUser";
	public static final String MANAGE_USERS = "/user/*";
	public static final String DELET_USER = "/delete-User";
	public static final String CHANGE_PASSWORD = "/user/change-password";
	public static final String CHANGE_USERNAME = "/user/change-username";
	public static final String CREATE_FIRST_USER = "/views/CreateUser.jsp";
	public static final String CREATE_CUSTOMER = "customer/create-customer";
	public static final String MANAGE_CUSTOMERS = "/customer/*";
	public static final String MANAGE_ITEMS = "/item/*";
	public static final String CASHIER = "/cashier/*";
	public static final String MANAGE_INVOICES = "/invoice/*";

	private ButtonPath() {
	}
}
