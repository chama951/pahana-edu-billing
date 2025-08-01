package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.PahanaEduException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createCustomer(Customer newCustomer) throws PahanaEduException, SQLException {

		checkExist(newCustomer);
		customerDao.createCustomer(newCustomer);

	}

	@Override
	public void updateCustomer(Customer customerToUpdate) throws PahanaEduException, SQLException {

		checkExist(customerToUpdate);
		customerDao.updateCustomer(customerToUpdate);

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

	public void checkExist(Customer customer) throws PahanaEduException {

		try {

			if (customerDao.existByAccNo(customer.getAccountNumber(), customer.getId())) {
				throw new PahanaEduException(
						MessageConstants.CUSTOMER_NUMBER_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

			if (customerDao.existByEmail(customer.getEmail(), customer.getId())) {
				throw new PahanaEduException(
						MessageConstants.CUSTOMER_EMAIL_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

			if (customerDao.existByPhoneNumber(customer.getPhoneNumber(), customer.getId())) {
				throw new PahanaEduException(
						MessageConstants.CUSTOMER_PHONENO_EXISTS,
						ButtonPath.MANAGE_CUSTOMERS);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
