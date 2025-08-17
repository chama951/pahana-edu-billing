package com.pahana.edu.utill.responseHandling;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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

	public static void handleValidationError(HttpServletRequest request, HttpServletResponse response,
			List<String> validationErrors, String redirectPath)
			throws ServletException, IOException {

		// Combine the main message with bullet points for each validation error
		StringBuilder fullErrorMessage = new StringBuilder();
		for (String error : validationErrors) {
			fullErrorMessage.append(" ").append(error).append("\n");
		}

		request.setAttribute("errorMessage", fullErrorMessage.toString());
		request.setAttribute("buttonPath", redirectPath);
		request.getRequestDispatcher("/views/ValidationMessege.jsp").forward(request, response);
	}

	public static void handleValidationError(HttpServletRequest request, HttpServletResponse response,
			String singleValidationError, String redirectPath)
			throws ServletException, IOException {
		handleValidationError(request, response,
				Collections.singletonList(singleValidationError), redirectPath);
	}

}
