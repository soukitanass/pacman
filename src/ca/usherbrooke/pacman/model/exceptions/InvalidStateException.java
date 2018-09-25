package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidStateException extends Exception {
  public InvalidStateException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidStateException(String errorMessage) {
    super(errorMessage);
  }
}
