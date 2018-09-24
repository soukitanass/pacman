package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidDigitException extends Exception {
  public InvalidDigitException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidDigitException(String errorMessage) {
    super(errorMessage);
  }
}
