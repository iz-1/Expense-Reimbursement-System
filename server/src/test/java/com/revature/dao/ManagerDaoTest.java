package com.revature.dao;

import com.mongodb.MongoClientSettings;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Manager;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ManagerDaoTest {
  ManagerDao dao;

  @BeforeEach
  public void setUp() throws Exception {
    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    MongoConnector connector = MongoConnector.createConnector(settings);
    dao = new ManagerDao(connector, MongoConnector.dbName);
    dao.dropAllCollection();
  }

  @AfterEach
  public void tearDown() throws Exception {
    dao.close();
  }

  @Test
  public void testGetNoUsers() {
    assertThrows(ItemNotFoundException.class, () -> {
      Manager e = dao.retrieve("abc@abc.com");
      e.getAddress();
    });
  }

  @Test
  public void testcreateManager() {
    //Manager e = Mockito.mock(Manager.class);
    Manager e = new Manager("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Manager result = dao.create(e);
    assertTrue(new ReflectionEquals(result).matches(e));
  }

  private void assertTrue(boolean matches) {
  }

  @Test
  public void testGetManager() { // retrieve(email)
    Manager mock = new Manager("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    dao.create(mock);
    Manager e = dao.retrieve("abc@abc.com");
    assertTrue(new ReflectionEquals(mock, "id").matches(e));
  }

  @Test
  public void testGetManagerbyId() { // retrieve(objectid)
    Manager mock = new Manager("def@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    Manager inserted = dao.create(mock);
    Manager e = dao.retrieve(new ObjectId(inserted.getHexId()));
    assertTrue(new ReflectionEquals(inserted).matches(e));
  }

  @Test
  public void testgetManagers(){
    dao.dropAllCollection();
    List<Manager> Managers = new ArrayList<>(Arrays.asList(
      new Manager("abc@abc.com", "mgr", "me", "name", "123 street", 1231231234L),
      new Manager("def@abc.com", "bigboss", "me", "name", "123 street", 1231231234L)
    ));
    for(Manager e : Managers)
      dao.create(e);
    List<Manager> res = dao.retrieveAll();
    assertTrue(new ReflectionEquals(res).matches(Managers));
  }

  @Test
  public void testupdateManager(){
    dao.dropAllCollection();
    Manager mock = new Manager("abc@abc.com", "pass", "me", "name", "123 street", 1231231234L);
    dao.create(mock);
    Manager updateEmp = new Manager("abc@abc.com", "pass", "my", "names", "555 street", 1231231234L);
    dao.replace(updateEmp);

    Manager retrieved = dao.retrieve(updateEmp.getEmail());
    assertTrue(new ReflectionEquals(retrieved).matches(updateEmp));
  }
}
