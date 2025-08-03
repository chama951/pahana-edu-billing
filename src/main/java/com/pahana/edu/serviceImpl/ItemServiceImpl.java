package com.pahana.edu.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.dao.ItemDao;
import com.pahana.edu.daoImpl.ItemDaoImpl;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;
import com.pahana.edu.utill.database.DBConnectionFactory;
import com.pahana.edu.utill.exception.PahanaEduException;
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
	public void createItem(Item newItem) throws PahanaEduException, SQLException {
		checkItemExist(newItem);
		itemDao.createItem(newItem);
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
	public void updateItem(Item itemToUpdate) throws PahanaEduException, SQLException {
		checkItemExist(itemToUpdate);
		itemDao.updateItem(itemToUpdate);
	}

	private void checkItemExist(Item item) throws PahanaEduException {
		try {
			if (itemDao.existByIsbn(item.getIsbn(), item.getId())) {
				throw new PahanaEduException(
						MessageConstants.ISBN_NUMBER_EXISTS,
						ButtonPath.MANAGE_ITEMS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
