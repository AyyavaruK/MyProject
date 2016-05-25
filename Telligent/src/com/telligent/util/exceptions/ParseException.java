package com.telligent.util.exceptions;

/**
  * Signals that an error has been reached unexpectedly while parsing.
*/
public class ParseException extends Exception {
  public ParseException() {
    super();
  } 

  public ParseException(String message) {
    super(message);
  } 

  public ParseException(String message, Throwable cause) {
    super(message, cause);
  } 

  public ParseException(Throwable cause) {
    super(cause);
  }
}