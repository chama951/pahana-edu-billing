package com.pahana.edu.utill;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseHandler {

	private ResponseHandler() throws SQLException {
		// Private constructor to prevent instantiation
	}

	// Generic error handler
	public static void handleError(HttpServletRequest request, HttpServletResponse response,
			String errorMessage, String redirectPath, String buttonText)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("buttonPath", redirectPath);
		request.setAttribute("buttonValue", buttonText);
		request.getRequestDispatcher("/views/ErrorMessege.jsp").forward(request, response);
	}

	// Generic success handler
	public static void handleSuccess(HttpServletRequest request, HttpServletResponse response,
			String successMessage, String redirectPath, String buttonText)
			throws ServletException, IOException {
		request.setAttribute("successMessage", successMessage);
		request.setAttribute("buttonPath", redirectPath);
		request.setAttribute("buttonValue", buttonText);
		request.getRequestDispatcher("/views/ProcessDone.jsp").forward(request, response);
	}

	// Validation error handler
	public static void handleValidationError(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> errors, String redirectPath)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errors);
		request.setAttribute("buttonPath", redirectPath);
		request.setAttribute("buttonValue", "Go Back");
		request.getRequestDispatcher(redirectPath).forward(request, response);
	}

	// API error response (for REST controllers)
	public static void sendJsonError(HttpServletResponse response, int statusCode,
			String errorMessage) throws IOException {
		response.setContentType("application/json");
		response.setStatus(statusCode);
		response.getWriter().write(
				String.format("{\"status\":%d,\"message\":\"%s\"}", statusCode, errorMessage));
	}
}
