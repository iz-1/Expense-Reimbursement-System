package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.exception.ItemExistsException;
import com.revature.exception.ItemNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.model.RequestStatus;
import org.bson.types.ObjectId;

import java.util.List;


/**
 * reimbursement request services
 */
public class ReimbursementService implements Service<Reimbursement> {
  ReimbursementDao dao;

  /**
   *
   * @param dao
   */
  public ReimbursementService(ReimbursementDao dao) { this.dao = dao; }

  /**
   * creates a request
   * @param item request
   * @return created request
   * @throws ItemExistsException
   */
  @Override
  public Reimbursement create(Reimbursement item) throws ItemExistsException {
    item.setStatus(RequestStatus.pending);
    return dao.create(item);
  }

  /**
   * find request by hexId
   * @param searchField hexId
   * @return found request
   * @throws ItemNotFoundException
   */
  @Override
  public Reimbursement retrieve(String searchField) throws ItemNotFoundException {
    return dao.retrieve(new ObjectId(searchField)); //hexId
  }

  /**
   * find request
   * @param id ObjectId
   * @return found request
   * @throws ItemNotFoundException
   */
  @Override
  public Reimbursement retrieve(ObjectId id) throws ItemNotFoundException {
    return dao.retrieve(id);
  }

  /**
   * replace request with another
   * @param item request to replace with
   * @throws ItemNotFoundException
   */
  @Override
  public void replace(Reimbursement item) throws ItemNotFoundException {
    dao.replace(item);
  }

  /**
   * get all requests
   * @return requests
   * @throws ItemNotFoundException
   */
  @Override
  public List retrieveAll() throws ItemNotFoundException {
    return dao.retrieveAll();
  }

  /**
   * get all requests from a user
   * @param ownerHexId user's id
   * @return requests
   */
  public List retriveAllbyUser(String ownerHexId) {
    return dao.retriveAllbyUser(ownerHexId);
  }

  /**
   * reviews a pending request
   * @param r request to evaluate
   * @param status to change too
   * @param reviewerId manager's id
   * @return reviewed request
   */
  public Reimbursement reviewRequest(Reimbursement r, String status, String reviewerId) {
    RequestStatus rStat = RequestStatus.valueOf(status);
    if(rStat == RequestStatus.pending || r.getStatus() != RequestStatus.pending) return null;

    r.setStatus(rStat);
    r.setReviewerId(reviewerId);
    dao.replace(r);
    return dao.retrieve(new ObjectId(r.getHexId()));
  }
}
