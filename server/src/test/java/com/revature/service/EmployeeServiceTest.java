package com.revature.service;

import com.mongodb.MongoClientSettings;
import com.revature.model.Employee;
import com.revature.dao.EmployeeDao;
import com.revature.dao.MongoConnector;
import com.revature.utils.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
  EmployeeService service;
  EmployeeDao dao;
  MongoConnector connector;

  @BeforeEach
  void setUp() {
    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    connector = MongoConnector.createConnector(settings);
    dao = new EmployeeDao(connector, MongoConnector.dbName);
    service = new EmployeeService(dao);
    dao.dropAllCollection();
  }

  @AfterEach
  void tearDown() {
      assertDoesNotThrow(() -> connector.close());
  }

  @Test
  void create() {
    Employee e = Mock.getMockObjectFromJson("employee", Employee.class);
    Employee res = service.create(e);
    assertTrue(new ReflectionEquals(e, "id").matches(res));
  }

  @Test
  void retrieve() {
    Employee e = new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Employee result = service.create(e);
    Employee find = service.retrieve(e.getEmail()); // retrieve mail
    assertTrue(new ReflectionEquals(result).matches(find));
  }

  @Test
  void testReplaceEmployee() {
    Employee e = new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Employee res = service.create(e);

    e.setFirstName("newFirst");
    e.setLastName("newLast");
    e.setPhoneNumber(1334445555L);
    e.setAddress("12st street");
    service.replace(e);

    Employee result = service.retrieve(res.getEmail());
    assertTrue(new ReflectionEquals(e).matches(result));
  }

  @Test
  void retrieveAll() {
    List<Employee> employees = new ArrayList<>(Arrays.asList(
      new Employee("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L),
      new Employee("def@abc.com", "pass", "me", "name", "123 street", 1231231234L)
    ));
    for(Employee e : employees)
      service.create(e);
    List<Employee> res = service.retrieveAll();
    assertTrue(new ReflectionEquals(res).matches(employees));
  }
}
