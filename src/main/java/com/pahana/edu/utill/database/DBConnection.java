package com.pahana.edu.utill.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String DB_DRIVER;

	private static DBConnection instance;
	private Connection connection;

	private DBConnection() {
		try {
			// Load properties
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("/db.properties");
			Properties prop = new Properties();
			prop.load(input);

			// Assign values
			DB_URL = prop.getProperty("db.url");
			DB_USER = prop.getProperty("db.username");
			DB_PASSWORD = prop.getProperty("db.password");
			DB_DRIVER = prop.getProperty("db.driver");

			// Register driver
			Class.forName(DB_DRIVER);
			this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			synchronized (DBConnection.class) {
				if (instance == null) {
					instance = new DBConnection();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}
