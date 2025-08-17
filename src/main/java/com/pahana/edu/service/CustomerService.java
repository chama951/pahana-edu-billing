package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Customer;
import com.pahana.edu.utill.exception.MyCustomException;

public interface CustomerService {

	void createCustomer(Customer newCustomer) throws SQLException, MyCustomException;

	void updateCustomer(Customer customerToUpdate) throws SQLException, MyCustomException;

	void deleteCustomer(Long customerId) throws SQLException;

	List<Customer> getAllCustomers() throws SQLException;

	Customer getCustomerById(Long customerId) throws SQLException;

}
