package com.revature.exception;

/**
 * when item is not found in database
 */
public class ItemNotFoundException extends RuntimeException{
  public ItemNotFoundException(String message) {
    super(message);
  }
}
