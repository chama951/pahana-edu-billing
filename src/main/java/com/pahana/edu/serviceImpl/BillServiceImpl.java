package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.BillDao;
import com.pahana.edu.daoImpl.BillDaoImpl;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.BillService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.PahanaEduException;

public class BillServiceImpl implements BillService {
	private BillDao billDao = new BillDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createBill(List<Item> itemList, Long customerId, Long userId) throws PahanaEduException, SQLException {

		try {

			Bill bill = new Bill();
			BillItem billItem = new BillItem();

			Double discountAmount = 0.0;
			Double totalAmount = 0.0;

			if (itemList != null) {
				for (Item item : itemList) {
					discountAmount += item.getDiscountAmount() * item.getQuantityInStock();
					totalAmount += item.getPrice() * item.getQuantityInStock();
				}
				bill.setDiscountAmount(discountAmount);
				bill.setTotalAmount(totalAmount);
				bill.setNetAmount(totalAmount - discountAmount);
			}
			Long billId = billDao.createBill(bill, customerId, userId);
			Bill billInDb = billDao.getBillById(billId);

			for (Item item : itemList) {
				billItem.setBill(billInDb);
				billItem.setItem(item);
				billItem.setQuantity(item.getQuantityInStock());
				billItem.setDiscountAmount(item.getDiscountAmount() * item.getQuantityInStock());
				billItem.setSubTotal(item.getPrice() * item.getQuantityInStock());
				billItem.setUnitPrice(item.getPrice());
				billDao.createBillItem(billItem, billId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
