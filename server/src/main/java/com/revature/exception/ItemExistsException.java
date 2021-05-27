package com.revature.exception;

/**
 * when item cannot be created/already exists
 */
public class ItemExistsException extends RuntimeException {
  public ItemExistsException(String message) {
    super(message);
  }
}
