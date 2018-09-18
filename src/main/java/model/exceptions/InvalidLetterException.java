package model.exceptions;

@SuppressWarnings("serial")
public class InvalidLetterException extends Exception {
  public InvalidLetterException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidLetterException(String errorMessage) {
    super(errorMessage);
  }
}
