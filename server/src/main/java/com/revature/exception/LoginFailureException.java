package com.revature.exception;

/**
 * throw when login fails
 */
public class LoginFailureException extends RuntimeException{
  public LoginFailureException(String message) {
    super(message);
  }
}
