package com.revature.dao;

import com.mongodb.MongoClientSettings;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.RequestStatus;
import com.revature.utils.Mock;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementDaoTest {
ReimbursementDao dao;

  @BeforeEach
  void setUp() {
    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    MongoConnector connector = MongoConnector.createConnector(settings);
    dao = new ReimbursementDao(connector, MongoConnector.dbName);
    dao.dropAllCollection();
  }

  @AfterEach
  void tearDown() throws Exception {
    dao.close();
  }

  @Test
  public void testGetEmpty() {
    assertThrows(ItemNotFoundException.class, () ->{
      Reimbursement mock = Mock.getMockReimbursement();

      // set id
      mock.setId( new ObjectId().toHexString() );

      dao.retrieve(new ObjectId(mock.getHexId()));
    });
  }

  @Test
  public void testCreateRequest() {
    Reimbursement mock = Mock.getMockReimbursement();
    Reimbursement r = dao.create(mock);
    assertTrue(new ReflectionEquals(r).matches(mock)); // @failing
  }

  @Test
  public void testGetByHexId() {
    Reimbursement mock = Mock.getMockReimbursement();
    Reimbursement created = dao.create(mock);
    Reimbursement find = dao.retrieve(new ObjectId(created.getHexId()));
    assertTrue(new ReflectionEquals(find).matches(created));
  }

  @Test
  public void testGetRequestById() {
    Reimbursement mock = Mock.getMockReimbursement();
    Reimbursement created = dao.create(mock);
    Reimbursement find = dao.retrieve(new ObjectId(created.getHexId()));
    assertTrue(new ReflectionEquals(find).matches(mock)); // @failing*/
  }

  @Test
  public void testGetAllRequests() {
    List<Reimbursement> reimbursements = Mock.getMockReimbursmentItems();

    for(Reimbursement r : reimbursements)
      dao.create(r);

      List res = dao.retrieveAll();
      assertTrue(new ReflectionEquals(res).matches(reimbursements));
  }

  @Test
  public void testRetriveallbyUser() {
    String hexId = new ObjectId().toHexString(); // new user
    List<Reimbursement> reimbursements = Mock.getMockReimbursmentItems();
    for(Reimbursement r : reimbursements) {
      r.setRequesterId(hexId);
      dao.create(r);
    }

    List<Reimbursement> requestList = dao.retriveAllbyUser(hexId);
    assertTrue(new ReflectionEquals(reimbursements).matches(requestList));
  }

  @Test
  public void testReplace() {
    Reimbursement mock = Mock.getMockReimbursement();
    Reimbursement created = dao.create(mock);
    created.setStatus(RequestStatus.denied);
    created.setReviewerId("testReviewerId");
    dao.replace(created);
    Reimbursement replace = dao.retrieve(new ObjectId(created.getHexId()));

    assertTrue(new ReflectionEquals(created).matches(replace));
  }
}
