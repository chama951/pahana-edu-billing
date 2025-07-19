package com.pahana.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahana.edu.utill.database.DBConnectionFactory;

public class TestDBConn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestDBConn() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		PrintWriter printWriter = response.getWriter();

		printWriter.print("<h2> Pahana Edu DB Connection Object : " 
		+ DBConnectionFactory.getConnection() 
		+ "</h2>");

		getServletContext().setAttribute("pahana-edu-connection-db",
				DBConnectionFactory.getConnection());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
