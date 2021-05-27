package com.revature.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Employee object class
 */
public class Employee extends User {
  protected String firstName;
  protected String lastName;
  protected String address;
  protected Long phoneNumber;
  protected List<Integer> requestIds = new ArrayList<>();

  /**
   *
   */
  public Employee(){
    super();
  }

  /**
   *
   * @param email
   * @param password
   * @param firstName
   * @param lastName
   * @param address
   * @param phoneNumber
   */
  public Employee(String email, String password, String firstName, String lastName, String address, Long phoneNumber) {
    super(email, password);
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.type = UserType.employee;
  }

  /**
   *
   * @return
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   *
   * @param firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   *
   * @return
   */
  public String getLastName() {
    return lastName;
  }

  /**
   *
   * @param lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   *
   * @return
   */
  public String getAddress() {
    return address;
  }

  /**
   *
   * @param address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   *
   * @return
   */
  public Long getPhoneNumber() {
    return phoneNumber;
  }

  /**
   *
   * @param phoneNumber
   */
  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   *
   * @return
   */
  public List<Integer> getRequestIds() {
    return requestIds;
  }

  /**
   *
   * @param requestIds
   */
  public void setRequestIds(List<Integer> requestIds) {
    this.requestIds = requestIds;
  }

  /**
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;
    Employee employee = (Employee) o;
    return Objects.equals(getFirstName(), employee.getFirstName()) && Objects.equals(getLastName(), employee.getLastName()) && Objects.equals(getAddress(), employee.getAddress()) && Objects.equals(getPhoneNumber(), employee.getPhoneNumber()) && Objects.equals(getRequestIds(), employee.getRequestIds());
  }

  /**
   *
   * @return
   */
  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getLastName(), getAddress(), getPhoneNumber(), getRequestIds());
  }
}
