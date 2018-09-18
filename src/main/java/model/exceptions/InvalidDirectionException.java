package model.exceptions;

@SuppressWarnings("serial")
public class InvalidDirectionException extends Exception {
  public InvalidDirectionException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidDirectionException(String errorMessage) {
    super(errorMessage);
  }
}
