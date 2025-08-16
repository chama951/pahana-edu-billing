package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.BillDao;
import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.dao.ItemDao;
import com.pahana.edu.daoImpl.BillDaoImpl;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.daoImpl.ItemDaoImpl;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.service.BillService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.PahanaEduException;

public class BillServiceImpl implements BillService {
	private BillDao billDao = new BillDaoImpl(DBConnectionFactory.getConnection());
	private ItemDao itemDao = new ItemDaoImpl(DBConnectionFactory.getConnection());
	private CustomerDao customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createBill(List<Item> itemList, Long customerId, Long userId, BillStatus billStatus)
			throws PahanaEduException, SQLException {

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
				bill.setBillStatus(billStatus);
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

				Item itemInDb = itemDao.getItemById(item.getId());
				Integer newQty = itemInDb.getQuantityInStock() - billItem.getQuantity();
				itemInDb.setQuantityInStock(newQty);
				itemDao.updateItem(itemInDb);

				Customer customerInDb = customerDao.getCustomerById(customerId);
				Integer newUnitsConsumed = customerInDb.getUnitsConsumed() + billItem.getQuantity();
				customerInDb.setUnitsConsumed(newUnitsConsumed);
				customerDao.updateCustomer(customerInDb);

				billDao.createBillItem(billItem, billId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Bill> getAllBills() throws SQLException {
		try {
			List<Bill> billList = billDao.getAllBills();
			return billList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void changeStatus(Long billId, BillStatus billStatus) throws SQLException {
		try {
			Bill billInDb = new Bill();
			billInDb = billDao.getBillById(billId);
			billInDb.setBillStatus(billStatus);
			billDao.updateBill(billInDb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
