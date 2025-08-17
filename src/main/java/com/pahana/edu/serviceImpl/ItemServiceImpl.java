package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.ItemDao;
import com.pahana.edu.daoImpl.ItemDaoImpl;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.responseHandling.ButtonPath;
import com.pahana.edu.utill.responseHandling.MessageConstants;

public class ItemServiceImpl implements ItemService {
	private ItemDao itemDao = new ItemDaoImpl(DBConnectionFactory.getConnection());

	@Override
	public List<Item> getAllItems() throws SQLException {

		try {
			List<Item> itemList = itemDao.getAllItems();
			return itemList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createItem(Item newItem) throws MyCustomException, SQLException {

		double discountAmount = (newItem.getPrice() * newItem.getDiscountPercentage() / 100);
		newItem.setDiscountAmount(discountAmount);

		try {
			checkItemExist(newItem);
			itemDao.createItem(newItem);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteItem(Long itemId) throws SQLException {

		try {
			itemDao.deleteItem(itemId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateItem(Item itemToUpdate) throws MyCustomException, SQLException {

		double discountAmount = itemToUpdate.getPrice() * itemToUpdate.getDiscountPercentage() / 100;
		itemToUpdate.setDiscountAmount(discountAmount);

		try {
			checkItemExist(itemToUpdate);
			itemDao.updateItem(itemToUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void checkItemExist(Item item) throws MyCustomException {

		try {
			if (itemDao.existByIsbn(item.getIsbn(), item.getId())) {
				throw new MyCustomException(
						MessageConstants.ISBN_NUMBER_EXISTS,
						ButtonPath.MANAGE_ITEMS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Item getItemById(Long itemId) {
		try {
			Item item = itemDao.getItemById(itemId);
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
