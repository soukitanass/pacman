package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidColorException extends Exception {
  public InvalidColorException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidColorException(String errorMessage) {
    super(errorMessage);
  }
}
