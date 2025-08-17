package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createCustomer(Customer newCustomer) throws MyCustomException, SQLException {
		try {
			checkCustomerExist(newCustomer);
			customerDao.createCustomer(newCustomer);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateCustomer(Customer customerToUpdate) throws MyCustomException, SQLException {
		try {
			checkCustomerExist(customerToUpdate);
			customerDao.updateCustomer(customerToUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCustomer(Long customerId) throws SQLException {
		try {
			customerDao.deleteCustomer(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws SQLException {
		try {
			List<Customer> customerList = customerDao.getAllCustomers();
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void checkCustomerExist(Customer customer) throws MyCustomException {

		try {

			if (customerDao.existByAccNo(customer.getAccountNumber(), customer.getId())) {
				throw new MyCustomException(
						MessageConstants.CUSTOMER_NUMBER_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

			if (customerDao.existByEmail(customer.getEmail(), customer.getId())) {
				throw new MyCustomException(
						MessageConstants.CUSTOMER_EMAIL_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

			if (customerDao.existByPhoneNumber(customer.getPhoneNumber(), customer.getId())) {
				throw new MyCustomException(
						MessageConstants.CUSTOMER_PHONENO_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Customer getCustomerById(Long customerId) throws SQLException {
		try {
			Customer customer = customerDao.getCustomerById(customerId);
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
