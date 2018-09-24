package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class InvalidSoundException extends Exception {
  public InvalidSoundException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public InvalidSoundException(String errorMessage) {
    super(errorMessage);
  }
}
