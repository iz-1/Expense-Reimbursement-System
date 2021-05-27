package com.revature.dao;

import com.mongodb.client.FindIterable;
import com.revature.exception.ItemExistsException;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * implements crud operations on reimbursement mongo db
 */
public class ReimbursementDao extends MongoDao<Reimbursement> implements Dao<Reimbursement> {
  final private static Logger logger = LogManager.getLogger(ReimbursementDao.class);

  /**
   *
   * @param connector
   * @param databaseName
   */
  public ReimbursementDao(MongoConnector connector, String databaseName) {
    super(connector, databaseName, Reimbursement.class);
  }

  /**
   * inserts a reimbursement to the database
   * @param item to be inserted
   * @return item inserted
   * @throws ItemExistsException
   */
  @Override
  public Reimbursement create(Reimbursement item) throws ItemExistsException {
    getItems(Reimbursement.class);
    return super.create(item);
  }

  /**
   * get a reimbursement by ObjectId
   * @param id to find
   * @return itme found
   * @throws ItemNotFoundException
   */
  @Override
  //@todo turn to hex string
  public Reimbursement retrieve(ObjectId id) throws ItemNotFoundException {
    Bson filter = getFilterByName("id", id);
    getItems(Reimbursement.class);
    return retrieve(filter);
  }

  /**
   * replace a reimbursement
   * @param item item to replace with
   * @throws ItemNotFoundException
   */
  @Override
  public void replace(Reimbursement item) throws ItemNotFoundException {
    super.replace(item, getFilterByName("id", new ObjectId(item.getHexId())));
  }

  /**
   * get all reimbursements
   * @return reimbursements
   * @throws ItemNotFoundException
   */
  @Override
  public List retrieveAll() throws ItemNotFoundException {
    return super.retrieveAll(Reimbursement.class);
  }

  /**
   * get all reimbursements by a users' id
   * @param ownerHexId requster's id
   * @return all of user's requests
   */
  public List retriveAllbyUser(String ownerHexId) {
    Bson filter = getFilterByName("requesterId", ownerHexId);
    getItems(Reimbursement.class);
    FindIterable<Reimbursement> res = this.items.find(filter);
    return (List<Reimbursement>) StreamSupport.stream(res.spliterator(), true).collect(Collectors.toList());
  }
}
