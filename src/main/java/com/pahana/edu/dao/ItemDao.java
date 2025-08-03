package com.pahana.edu.dao;

import java.sql.SQLException;
import java.util.List;

import com.pahana.edu.model.Item;

public interface ItemDao {

	List<Item> getAllItems() throws SQLException;

	boolean existByIsbn(String isbn, Long id) throws SQLException;

	void createItem(Item newItem) throws SQLException;

	void deleteItem(Long itemId) throws SQLException;

	void updateItem(Item itemToUpdate) throws SQLException;

}
