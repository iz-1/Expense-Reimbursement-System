package com.revature.service;

import com.revature.exception.ItemExistsException;
import com.revature.exception.ItemNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * service interfaces
 * @param <T>
 */
public interface Service<T> {
  T create(T item) throws ItemExistsException;
  T retrieve(String searchField) throws ItemNotFoundException;
  T retrieve(ObjectId id) throws ItemNotFoundException;
  void replace(T item) throws ItemNotFoundException;
  List retrieveAll() throws ItemNotFoundException;
}
