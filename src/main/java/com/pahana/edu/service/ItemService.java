package com.pahana.edu.service;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Item;
import com.pahana.edu.utill.exception.MyCustomException;
import com.pahana.edu.utill.exception.MyValidationException;

public interface ItemService {

	List<Item> getAllItems() throws SQLException;

	void createItem(Item newItem) throws MyCustomException, SQLException, MyValidationException;

	void deleteItem(Long itemId) throws SQLException;

	void updateItem(Item itemToUpdate) throws MyCustomException, SQLException, MyValidationException;

	Item getItemById(Long itemId);

}
