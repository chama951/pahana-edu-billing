package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Customer;
import com.pahana.edu.utill.exception.DuplicateEntryException;

public interface CustomerService {

	void createCustomer(Customer newCustomer) throws SQLException, DuplicateEntryException;

	void updateCustomer(Customer customerToUpdate) throws SQLException, DuplicateEntryException;

	void deleteCustomer(Long customerId) throws SQLException;

	List<Customer> getAllCustomers() throws SQLException;

}
