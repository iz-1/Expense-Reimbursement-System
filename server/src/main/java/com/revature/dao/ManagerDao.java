package com.revature.dao;

import com.revature.exception.ItemNotFoundException;
import com.revature.exception.ItemExistsException;
import com.revature.model.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * implements crud operations on manager mongo db
 */
public class ManagerDao extends MongoDao<Manager> implements Dao<Manager> {
  final private static Logger logger = LogManager.getLogger(ManagerDao.class);

  /**
   *
   * @param connector
   * @param databaseName
   */
  public ManagerDao(MongoConnector connector, String databaseName) {
    super(connector, databaseName, Manager.class);
  }

  /**
   * inserts a manager into the database
   * @param m manager to insert
   * @return manager inserted
   * @throws ItemExistsException
   */
  @Override
  public Manager create(Manager m) throws ItemExistsException {
    Bson filter = getFilterByName("email", m.getEmail());
    try {
      Manager mgr = retrieve(filter);
      if(mgr != null) {
        logger.warn("exists: " + mgr.getEmail());
        throw new ItemExistsException("exists: " + mgr.getEmail());
      }
    }catch(ItemNotFoundException ex) {
      //expected catch
    }
    getItems(Manager.class);
    return super.create(m);
  }

  /**
   * gets a manager by email
   * @param email
   * @return found manager
   * @throws ItemNotFoundException
   */
  public Manager retrieve(String email) throws ItemNotFoundException {
    getItems(Manager.class);
    Bson filter = getFilterByName("email", email);
    return super.retrieve(filter);
  }

  /**
   * gets a manager by ObjectId
   * @param id item to find
   * @return found manager
   * @throws ItemNotFoundException
   */
  @Override
  public Manager retrieve(ObjectId id) throws ItemNotFoundException {
    Bson filter = getFilterByName("id", id);
    return retrieve(filter);
  }

  /**
   * replaces a manager in the database
   * @param m manager to replace with
   * @throws ItemNotFoundException
   */
  @Override
  public void replace(Manager m) throws ItemNotFoundException {
    Bson filter = getFilterByName("email", m.getEmail());
    Manager find = super.retrieve(filter);
    m.setId(find.getHexId());
    super.replace(m, filter);
  }

  /**
   * get all managers
   * @return
   * @throws ItemNotFoundException
   */
  @Override
  public List<Manager> retrieveAll() throws ItemNotFoundException {
    return super.retrieveAll(Manager.class);
  }
}
