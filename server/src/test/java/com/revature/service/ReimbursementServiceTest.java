package com.revature.service;

import com.mongodb.MongoClientSettings;
import com.revature.dao.MongoConnector;
import com.revature.dao.ReimbursementDao;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.RequestStatus;
import com.revature.utils.Mock;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {
  ReimbursementService service;
  ReimbursementDao dao;
  MongoConnector connector;

  @BeforeEach
  void setUp() {
    MongoClientSettings settings = MongoConnector.defaultSettings(MongoConnector.serverUrl + MongoConnector.dbName);
    connector = MongoConnector.createConnector(settings);
    dao = new ReimbursementDao(connector, MongoConnector.dbName);
    service = new ReimbursementService(dao);
    dao.dropAllCollection();
  }

  @AfterEach
  void tearDown() {
    assertDoesNotThrow(() -> connector.close());
  }

  @Test
  void create() {
    String type = "reimbursement";
    Reimbursement r = Mock.getMockObjectFromJson(type, Reimbursement.class);
    Reimbursement res = service.create(r);

    assertTrue( res.equals(r));
  }

  @Test
  void testRetrieve() {
    assertThrows(ItemNotFoundException.class, () ->{
      Reimbursement mock = Mock.getMockReimbursement();

      // set id
      mock.setId( new ObjectId().toHexString() );

      service.retrieve(new ObjectId(mock.getHexId()));
    });
  }

  @Test
  void testUpdate() {
    Reimbursement mock = Mock.getMockReimbursement();

    Reimbursement res = service.create(mock);
    String reviewId = new ObjectId().toHexString();
    res.setReviewerId(reviewId);
    res.setStatus(RequestStatus.denied);
    service.replace(res);

    Reimbursement changed = service.retrieve(new ObjectId(res.getHexId()));
    assertTrue(new ReflectionEquals(res).matches(changed));
  }

  @Test
  void testRetrieveAll() {
    List<Reimbursement> reimbursements = Mock.getMockReimbursmentItems();

    for(Reimbursement r : reimbursements)
      service.create(r);

    List res = service.retrieveAll();
    assertTrue(new ReflectionEquals(res).matches(reimbursements));
  }

  @Test
  void testRetrievAllbyUser() {
    String hexId = new ObjectId().toHexString(); // new user
    List<Reimbursement> reimbursements = Mock.getMockReimbursmentItems();
    for(Reimbursement r : reimbursements) {
      r.setRequesterId(hexId);
      service.create(r);
    }

    List<Reimbursement> requestList = service.retriveAllbyUser(hexId);
    assertTrue(new ReflectionEquals(reimbursements).matches(requestList));
  }
}
