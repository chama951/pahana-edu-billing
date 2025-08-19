package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pahana.edu.dao.BillDao;
import com.pahana.edu.dao.CustomerDao;
import com.pahana.edu.dao.ItemDao;
import com.pahana.edu.dao.UserDao;
import com.pahana.edu.daoImpl.BillDaoImpl;
import com.pahana.edu.daoImpl.CustomerDaoImpl;
import com.pahana.edu.daoImpl.ItemDaoImpl;
import com.pahana.edu.daoImpl.UserDaoImpl;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.model.Customer;
import com.pahana.edu.model.Item;
import com.pahana.edu.model.User;
import com.pahana.edu.model.enums.BillStatus;
import com.pahana.edu.service.BillService;
import com.pahana.edu.utill.BillGenerate;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class BillServiceImpl implements BillService {
	private BillDao billDao = new BillDaoImpl(DBConnectionFactory.getConnection());
	private ItemDao itemDao = new ItemDaoImpl(DBConnectionFactory.getConnection());
	private CustomerDao customerDao = new CustomerDaoImpl(DBConnectionFactory.getConnection());
	private UserDao userDao = new UserDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public void createBill(List<Item> itemList, Long customerId, Long userId, BillStatus billStatus)
			throws MyCustomException, SQLException {

		try {
			Bill bill = new Bill();
			Double discountAmount = 0.0;
			Double totalAmount = 0.0;

			for (Item item : itemList) {
				discountAmount += item.getDiscountAmount() * item.getQuantityInStock();
				totalAmount += item.getPrice() * item.getQuantityInStock();
			}

			bill.setDiscountAmount(discountAmount);
			bill.setTotalAmount(totalAmount);
			bill.setNetAmount(totalAmount - discountAmount);
			bill.setBillStatus(billStatus);

			Long billId = billDao.createBill(bill, customerId, userId);
			Bill billInDb = billDao.getBillById(billId);
			List<BillItem> billItems = new ArrayList<>();
			Customer customerInDb = customerDao.getCustomerById(customerId);
			User currentUser = userDao.getUserById(userId);

			for (Item item : itemList) {
				BillItem billItem = new BillItem();
				billItem.setBill(billInDb);
				billItem.setItem(item);
				billItem.setQuantity(item.getQuantityInStock());
				billItem.setDiscountAmount(item.getDiscountAmount() * item.getQuantityInStock());
				billItem.setSubTotal(item.getPrice() * item.getQuantityInStock());
				billItem.setUnitPrice(item.getPrice());

				Item itemInDb = itemDao.getItemById(item.getId());
				if (itemInDb.getQuantityInStock() < item.getQuantityInStock()) {
					throw new MyCustomException("Insufficient stock for item: " + item.getTitle(),
							ButtonPath.CASHIER);
				}
				Integer newQty = itemInDb.getQuantityInStock() - item.getQuantityInStock();
				itemInDb.setQuantityInStock(newQty);
				itemDao.updateItem(itemInDb);

				Integer newUnitsConsumed = customerInDb.getUnitsConsumed() + item.getQuantityInStock();
				customerInDb.setUnitsConsumed(newUnitsConsumed);
				customerDao.updateCustomer(customerInDb);

				billDao.createBillItem(billItem, billId);
				billItems.add(billItem);
			}

			customerDao.updateCustomer(customerInDb);
			BillGenerate billGenerate = new BillGenerate();
			billGenerate.generateInvoicePdf(billInDb, billItems, customerInDb, currentUser);

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (MyCustomException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyCustomException(MessageConstants.FAILED_GENERATE_INVOICE, ButtonPath.CASHIER);
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

	@Override
	public List<BillItem> getBillItemList(Long billId) throws SQLException {
		try {
			List<BillItem> billItems = billDao.getBillItemList(billId);
			return billItems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Bill getBillById(Long billId) throws SQLException {
		try {
			Bill billInDb = billDao.getBillById(billId);
			return billInDb;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
