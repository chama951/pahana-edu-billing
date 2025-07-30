package com.pahana.edu.controller.user.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahana.edu.utill.ButtonValues;
import com.pahana.edu.utill.ButtonPath;
import com.pahana.edu.utill.MessageConstants;
import com.pahana.edu.utill.ResponseHandler;

public class LogoutUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutUserServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		ResponseHandler.handleSuccess(request, response,
				MessageConstants.LOGOUT_SUCCESS, ButtonPath.LOGIN, ButtonValues.LOGIN);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
