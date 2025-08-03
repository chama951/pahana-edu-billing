package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Item;
import com.pahana.edu.utill.exception.PahanaEduException;

public interface ItemService {

	List<Item> getAllItems() throws SQLException;

	void createItem(Item newItem) throws PahanaEduException, SQLException;

	void deleteItem(Long itemId) throws SQLException;

	void updateItem(Item itemToUpdate) throws PahanaEduException, SQLException;

}
