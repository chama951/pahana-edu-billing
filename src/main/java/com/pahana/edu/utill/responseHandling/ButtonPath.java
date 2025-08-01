package com.pahana.edu.utill.responseHandling;

public final class ButtonPath {
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String DASHBOARD = "/views/Dashboard.jsp";
	public static final String CREATE_USER = "/create-user";
	public static final String UPDATE_USER = "/update-user";
	public static final String DELETE_USER = "/deleteUser";
	public static final String MANAGE_USERS = "/user/*";
	public static final String DELET_USER = "/delete-User";
	public static final String CHANGE_PASSWORD = "/change-password";
	public static final String CHANGE_USERNAME = "/change-username";
	public static final String CREATE_FIRST_USER = "/create-first-user";
	public static final String CREATE_CUSTOMER = "/create-customer";
	public static final String MANAGE_CUSTOMERS = "/customer/*";

	// Prevent instantiation
	private ButtonPath() {
	}
}
