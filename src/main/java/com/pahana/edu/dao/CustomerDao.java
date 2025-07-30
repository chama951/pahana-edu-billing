package com.pahana.edu.dao;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Customer;

public interface CustomerDao {

	void createCustomer(Customer customer) throws SQLException;

	List<Customer> getAllCustomers() throws SQLException;

	Customer getCustomerById(Long id) throws SQLException;

	boolean checkCustomerByIdAndPhoneNo(String phoneNo, Long customerId) throws SQLException;

	boolean checkCustomerByIdAndEmail(String email, Long customerId) throws SQLException;

	boolean checkCustomerByIdAndAccNo(Long accNo, Long customerId) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	void deleteCustomer(Long id) throws SQLException;

	boolean checkCustomerByAccNo(Long accountNumber) throws SQLException;

	boolean checkCustomerByPhoneNo(String phoneNumber) throws SQLException;

	boolean checkCustomerByAccNo(String email) throws SQLException;
}
