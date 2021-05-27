package com.revature.model;

import java.util.Objects;

/**
 * Expense class used in Reimbursement
 */
public class Expense {
  String expense;
  String date;  // in mm-dd-yyyy format
  long amount;  // in cents

  /**
   *
   */
  public Expense() {
  }

  /**
   *
   * @param expense
   * @param amount
   * @param date
   */
  public Expense(String expense, long amount, String date) {
    this.expense = expense;
    this.amount = amount;
    this.date = date;
  }

  /**
   *
   * @return
   */
  public String getExpense() {
    return expense;
  }

  /**
   *
   * @param expense
   */
  public void setExpense(String expense) {
    this.expense = expense;
  }

  /**
   *
   * @return
   */
  public long getAmount() {
    return amount;
  }

  /**
   *
   * @param amount
   */
  public void setAmount(long amount) {
    this.amount = amount;
  }

  /**
   *
   * @return
   */
  public String getDate() {
    return date;
  }

  /**
   *
   * @param date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Expense)) return false;
    Expense expense1 = (Expense) o;
    return getAmount() == expense1.getAmount() && Objects.equals(getExpense(), expense1.getExpense()) && Objects.equals(getDate(), expense1.getDate());
  }
}
