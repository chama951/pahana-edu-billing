package com.pahana.edu.utill.database;

import java.sql.Connection;

public class DBConnectionFactory {

	public static Connection getConnection() {
		return DBConnection.getInstance().getConnection();
	}
}
