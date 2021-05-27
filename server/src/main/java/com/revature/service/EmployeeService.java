package com.revature.service;

import com.revature.dao.EmployeeDao;
import com.revature.exception.ItemExistsException;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Employee;
import com.revature.model.UserType;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * employee services
 */
public class EmployeeService implements Service<Employee> {
  EmployeeDao dao;

  /**
   *
   * @param dao
   */
  public EmployeeService(EmployeeDao dao) {
    this.dao = dao;
  }

  /**
   * creates an employee
   * @param item employee to create
   * @return created employee
   * @throws ItemExistsException
   */
  @Override
  public Employee create(Employee item) throws ItemExistsException {
    item.setType(UserType.employee);
    return dao.create(item);
  }

  /**
   * gets an employee
   * @param searchField email
   * @return found employee
   * @throws ItemNotFoundException
   */
  @Override
  public Employee retrieve(String searchField) throws ItemNotFoundException {
    return dao.retrieve(searchField); // email
  }

  /**
   * gets an employee
   * @param id ObjectId
   * @return found employee
   * @throws ItemNotFoundException
   */
  @Override
  public Employee retrieve(ObjectId id) throws ItemNotFoundException {
    return dao.retrieve(id);
  }

  /**
   * updates an employee from profile changes
   * @param item changed employee fields
   * @throws ItemNotFoundException
   */
  @Override
  public void replace(Employee item) throws ItemNotFoundException {
    Employee existingRecord = dao.retrieve(item.getEmail());
    //update all valid fields
    existingRecord.setFirstName(item.getFirstName());
    existingRecord.setLastName(item.getLastName());
    existingRecord.setAddress(item.getAddress());
    existingRecord.setPhoneNumber(item.getPhoneNumber());
    //email cannot be changed by user
    dao.replace(existingRecord);
  }

  /**
   * get all employees
   * @return employees
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
    Employee existingRecord = dao.retrieve(new ObjectId(hexId));
    existingRecord.setPassword(password);
    dao.replace(existingRecord);
  }
}
