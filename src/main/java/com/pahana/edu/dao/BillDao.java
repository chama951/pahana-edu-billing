package com.pahana.edu.dao;

import java.sql.SQLException;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;

public interface BillDao {

	Long createBill(Bill bill, Long customerId, Long userId) throws SQLException;

	Bill getBillById(Long billId) throws SQLException;

	void createBillItem(BillItem billItem, Long billId) throws SQLException;

}
