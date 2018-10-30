package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidGhostNameException extends Exception {

  public InvalidGhostNameException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidGhostNameException(String errorMessage) {
    super(errorMessage);
  }
}
