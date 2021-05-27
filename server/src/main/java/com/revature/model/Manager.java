package com.revature.model;

import java.math.BigInteger;

/**
 * Manager with elevated functionality
 */
public class Manager extends Employee {
  public Manager(String email, String password, String firstName, String lastName, String address, Long phoneNumber) {
    super(email, password, firstName, lastName, address, phoneNumber);
    this.type = UserType.manager;
  }

  /**
   *
   */
  public Manager(){super();}
}
