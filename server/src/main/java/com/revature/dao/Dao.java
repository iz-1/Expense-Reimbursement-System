package com.revature.dao;

import com.revature.exception.ItemNotFoundException;
import com.revature.exception.ItemExistsException;
import com.revature.model.Employee;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * dao interfaces
 */
public interface Dao<T> {
  T create(T item) throws ItemExistsException;
  //T retrieve(String email) throws ItemNotFoundException;
  T retrieve(ObjectId id) throws ItemNotFoundException;
  void replace(T item) throws ItemNotFoundException;
  List retrieveAll() throws ItemNotFoundException;
}
