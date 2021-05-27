package com.revature.dao;

import com.revature.exception.ItemNotFoundException;
import com.revature.exception.ItemExistsException;
import com.revature.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * implements crud operations on employee mongo db
 */
public class EmployeeDao extends MongoDao<Employee> implements Dao<Employee> {
  final private static Logger logger = LogManager.getLogger(EmployeeDao.class);

  /**
   *
   * @param connector
   * @param databaseName
   */
  public EmployeeDao(MongoConnector connector, String databaseName) {
    super(connector, databaseName, Employee.class);
  }

  /**
   * creates employee if it does not exist
   * @param e employee to create
   * @return successfully created employee
   */
  @Override
  public Employee create(Employee e) {
    Bson filter = getFilterByName("email", e.getEmail());
    try {
      Employee emp = retrieve(filter);
      if(emp != null) {
        logger.warn("exists: " + emp.getEmail());
        throw new ItemExistsException("exists: " + emp.getEmail());
      }
    }catch(ItemNotFoundException ex) {
      //expected catch
    }
    getItems(Employee.class);
    return super.create(e);
  }

  /**
   * find employee by email
   * @param email
   * @return found employee
   */
  public Employee retrieve(String email) {
    getItems(Employee.class);
    Bson filter = getFilterByName("email", email);
    return super.retrieve(filter);
  }

  /**
    find item by ObjectID
   * @param id
   * @return item found
   * @throws ItemNotFoundException
   */
  @Override
  public Employee retrieve(ObjectId id) throws ItemNotFoundException {
    Bson filter = getFilterByName("id", id);
    return retrieve(filter);
  }

  /**
   * replaces an employee with another version
   * @param e updated employee
   * @throws ItemNotFoundException
   */
  public void replace(Employee e) throws ItemNotFoundException {
    Bson filter = getFilterByName("email", e.getEmail());
    Employee find = super.retrieve(filter);
    e.setId(find.getHexId());
    super.replace(e, filter);
  }

  /**
   * get all employee
   * @return employees
   * @throws ItemNotFoundException
   */
  @Override
  public List<Employee> retrieveAll() throws ItemNotFoundException {
    return super.retrieveAll(Employee.class);
  }
}
