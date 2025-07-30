package com.pahana.edu.utill;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.model.User;

public class AuthHelper {

	public static boolean isUserLoggedIn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLoggedIn = (User) session.getAttribute("currentUser");
		if (userLoggedIn == null) {
			ResponseHandler.handleError(request, response,
					MessageConstants.MUST_BE_LOGGED_IN, ButtonPath.LOGIN, ButtonValues.LOGIN);
			return false;
		}
		return true;
	}
}
