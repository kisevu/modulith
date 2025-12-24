package com.ameda.kev.smartparkingmodulith.entry.vo.domain;

/**
 * Author: kev.Ameda
 */
public class NullElementInCollectionException extends AssertionException{
  public NullElementInCollectionException(String field) {
    super(field, message(field));
  }

  private static String message(String field) {
    return new StringBuilder().append("The field \"").append(field).append("\" contains a null element").toString();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NULL_ELEMENT_IN_COLLECTION;
  }
}
