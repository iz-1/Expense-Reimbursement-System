package com.revature.model;

import org.bson.types.ObjectId;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * reimbursement data object
 */
public class Reimbursement extends MongoObject {
  List<Expense> expenses = new ArrayList<>(); //itemized list
  String requesterId;
  String reviewerId;
  RequestStatus status;
  String startDate;
  String endDate;
  long total;   // in cents

  /**
   * updates the total amount of reimbursement
   * @return
   */
  public long calcTotal() {
    total = expenses.stream().map(Expense::getAmount).reduce(Long::sum).get();
    return total;
  }

  /**
   * updates the date range from the expense list
   */
  public void updateDates() {
    for(Expense e : expenses){
      SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");

      try {
        if (startDate == null)
          startDate = e.date;
        else {
          if (sdf.parse(e.date).getTime() < sdf.parse(startDate).getTime())
            startDate = e.date;
        }

        if (endDate == null)
          endDate = e.date;
        else {
          if (sdf.parse(e.date).getTime() > sdf.parse(endDate).getTime())
            endDate = e.date;
        }
      }catch(ParseException ex) {
        System.out.println(ex.getMessage());
      }
    }

  }

  /**
   *
   */
  public Reimbursement(){}

  /**
   *
    * @param requesterId
   * @param expenses
   */
  public Reimbursement(String requesterId, List<Expense> expenses) {
    this.requesterId = requesterId;
    this.expenses = expenses;
    this.status = RequestStatus.pending;
    calcTotal();
    updateDates();
  }

  /**
   *
   * @return
   */
  public String getRequesterId() {
    return requesterId;
  }

  /**
   *
   * @param requesterId
   */
  public void setRequesterId(String requesterId) {
    this.requesterId = requesterId;
  }

  /**
   *
   * @return
   */
  public String getReviewerId() {
    return reviewerId;
  }

  /**
   *
   * @param reviewerId
   */
  public void setReviewerId(String reviewerId) {
    this.reviewerId = reviewerId;
  }

  /**
   *
   * @return
   */
  public long getTotal() {
    return total;
  }

  /**
   *
   * @param total
   */
  public void setTotal(long total) {
    this.total = total;
  }

  /**
   *
   * @return
   */
  public RequestStatus getStatus() {
    return status;
  }

  /**
   *
   * @param status
   */
  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  /**
   *
   * @return
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   *
   * @param startDate
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   *
   * @return
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   *
   * @param endDate
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   *
   * @return
   */
  public List<Expense> getExpenses() {
    return expenses;
  }

  /**
   *
   * @param expenses
   */
  public void setExpenses(List<Expense> expenses) {
    this.expenses = expenses;
    calcTotal();
  }

  /**
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Reimbursement)) return false;
    Reimbursement that = (Reimbursement) o;
    return getTotal() == that.getTotal() && Objects.equals(getExpenses(), that.getExpenses()) && Objects.equals(getRequesterId(), that.getRequesterId()) && Objects.equals(getReviewerId(), that.getReviewerId()) && getStatus() == that.getStatus() && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate());
  }
}
