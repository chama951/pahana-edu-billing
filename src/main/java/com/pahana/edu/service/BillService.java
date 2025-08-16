package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Bill;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.utill.exception.PahanaEduException;

public interface BillService {

	void createBill(List<Item> itemList, Long customerId, Long userId, BillStatus billStatus)
			throws PahanaEduException, SQLException;

	List<Bill> getAllBills() throws SQLException;

	void changeStatus(Long billId, BillStatus billStatus) throws SQLException;

}
