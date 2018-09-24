package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidScoreException extends Exception {
  public InvalidScoreException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidScoreException(String errorMessage) {
    super(errorMessage);
  }
}
