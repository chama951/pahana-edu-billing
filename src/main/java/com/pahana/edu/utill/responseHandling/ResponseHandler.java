package com.pahana.edu.utill.responseHandling;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseHandler {

	private ResponseHandler() throws SQLException {
		// Private constructor to prevent instantiation
	}

	// Generic error handler
	public static void handleError(HttpServletRequest request, HttpServletResponse response,
			String errorMessage)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
	}

	// Generic success handler
	public static void handleSuccess(HttpServletRequest request, HttpServletResponse response,
			String successMessage)
			throws ServletException, IOException {
		request.setAttribute("successMessage", successMessage);
		request.getRequestDispatcher("/views/successMessage.jsp").forward(request, response);
	}

	// Generic error handler with button path
	public static void handleError(HttpServletRequest request, HttpServletResponse response,
			String errorMessage, String redirectPath)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("buttonPath", redirectPath);
		request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
	}

	// Generic success handler with button path
	public static void handleSuccess(HttpServletRequest request, HttpServletResponse response,
			String successMessage, String redirectPath)
			throws ServletException, IOException {
		request.setAttribute("successMessage", successMessage);
		request.setAttribute("buttonPath", redirectPath);
		request.getRequestDispatcher("/views/successMessage.jsp").forward(request, response);
	}

}
