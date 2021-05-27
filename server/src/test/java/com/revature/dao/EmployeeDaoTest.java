package com.revature.dao;

import com.mongodb.MongoClientSettings;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Employee;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
//import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeDaoTest {
  EmployeeDao dao;

  @BeforeEach
  public void setUp() throws Exception {
    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    MongoConnector connector = MongoConnector.createConnector(settings);
    dao = new EmployeeDao(connector, MongoConnector.dbName);
    dao.dropAllCollection();
  }

  @AfterEach
  public void tearDown() throws Exception {
    dao.close();
  }

  @Test
  public void testGetNoUsers() { //retrieve email
    assertThrows(ItemNotFoundException.class, () -> {
      Employee e = dao.retrieve("abc@abc.com");
      e.getAddress();
    });
  }

  @Test
  public void testcreateEmployee() {
    //Employee e = Mockito.mock(Employee.class);
    Employee e = new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Employee result = dao.create(e);
    assertTrue(new ReflectionEquals(result).matches(e));
  }

  @Test
  public void testGetEmployee() {
    Employee mock = new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    dao.create(mock);
    Employee e = dao.retrieve("abc@abc.com");
    assertTrue(new ReflectionEquals(mock, "id").matches(e));
  }

  @Test
  public void testGetEmployeebyId() {
    Employee mock = new Employee("def@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Employee inserted = dao.create(mock);
    Employee e = dao.retrieve(inserted.getEmail());
    assertTrue(new ReflectionEquals(inserted).matches(e));
  }

  @Test
  public void testGetEmployeebybyObjectId() { // retrieve(ObjectId id)
    Employee mock = new Employee("def@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Employee inserted = dao.create(mock);
    Employee e = dao.retrieve(new ObjectId(inserted.getHexId()));
    assertTrue(new ReflectionEquals(inserted).matches(e));
  }

  @Test
  public void testgetEmployees(){ // retrieveAll
    List<Employee> employees = new ArrayList<>(Arrays.asList(
      new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L),
      new Employee("def@abc.com", "pass", "me", "name", "123 street", 1231231234L)
    ));
    for(Employee e : employees)
      dao.create(e);
    List<Employee> res = dao.retrieveAll();
    assertTrue(new ReflectionEquals(res).matches(employees));
  }

  @Test
  @Order(6)
  public void testupdateEmployee(){ // replace(Employee e)
    Employee mock = new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    dao.create(mock);
    Employee updateEmp = new Employee("abc@abc.com", "pass", "my", "names", "555 street", 1231231234L);
    dao.replace(updateEmp);

    Employee retrieved = dao.retrieve(updateEmp.getEmail());
    assertTrue(new ReflectionEquals(retrieved).matches(updateEmp));
  }
}
