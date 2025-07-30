package com.pahana.edu.dao;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Customer;

public interface CustomerDao {

	void createCustomer(Customer customer) throws SQLException;

	Customer getUserByAccNo(Long accNo) throws SQLException;

	List<Customer> getAllCustomers() throws SQLException;

	Customer getCustomerById(Long id) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	void deleteCustomer(Long id) throws SQLException;
}
