package com.revature.model;

/**
 * User data object
 */
abstract public class User extends MongoObject {
  protected String email; // login
  protected String password;
  protected UserType type;

  /**
   *
   */
  public User(){
    super();
  }

  /**
   *
   * @param email
   * @param password
   */
  public User(String email, String password) {
    super();
    this.email = email;
    this.password = password;
  }

  /**
   *
   * @return
   */
  public String getEmail() {
    return email;
  }

  /**
   *
   * @param email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   *
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   *
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   *
   * @return
   */
  public UserType getType() {
    return type;
  }

  /**
   *
   * @param type
   */
  public void setType(UserType type) {
    this.type = type;
  }
}
