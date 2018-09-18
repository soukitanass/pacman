package model.exceptions;

@SuppressWarnings("serial")
public class InvalidNumberException extends Exception {
  public InvalidNumberException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidNumberException(String errorMessage) {
    super(errorMessage);
  }
}
