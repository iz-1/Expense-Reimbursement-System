package com.revature.service;

import com.revature.dao.ManagerDao;
import com.revature.exception.ItemExistsException;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Employee;
import com.revature.model.Manager;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * manager services
 */
public class ManagerService implements Service<Manager> {
  ManagerDao dao;

  /**
   *
   * @param dao
   */
  public ManagerService(ManagerDao dao) {
    this.dao = dao;
  }

  /**
   * create a manager
   * @param item manager to create
   * @return created manager
   * @throws ItemExistsException
   */
  @Override
  public Manager create(Manager item) throws ItemExistsException {
    return dao.create(item);
  }

  /**
   * find manager
   * @param searchField email
   * @return found manager
   * @throws ItemNotFoundException
   */
  @Override
  public Manager retrieve(String searchField) throws ItemNotFoundException {
    return dao.retrieve(searchField); // email
  }

  /**
   * find manager
   * @param id Object Id
   * @return found manager
   * @throws ItemNotFoundException
   */
  @Override
  public Manager retrieve(ObjectId id) throws ItemNotFoundException {
    return dao.retrieve(id);
  }

  /**
   * replaces a manager
   * @param item manager to replace with
   * @throws ItemNotFoundException
   */
  @Override
  public void replace(Manager item) throws ItemNotFoundException {
    dao.replace(item);
  }

  /**
   * get all managers
   * @return managers
   * @throws ItemNotFoundException
   */
  @Override
  public List retrieveAll() throws ItemNotFoundException {
    return dao.retrieveAll();
  }

  /**
   *
   * @param hexId
   * @param password
   */
  public void updatePass(String hexId, String password) {
    Manager existingRecord = dao.retrieve(new ObjectId(hexId));
    existingRecord.setPassword(password);
    dao.replace(existingRecord);
  }
}
