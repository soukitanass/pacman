package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidWallCodeException extends Exception {
  public InvalidWallCodeException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidWallCodeException(String errorMessage) {
    super(errorMessage);
  }
}
