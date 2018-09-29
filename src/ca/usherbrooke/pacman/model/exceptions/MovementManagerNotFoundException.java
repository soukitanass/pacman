package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class MovementManagerNotFoundException extends Exception {

  public MovementManagerNotFoundException(String errorMessage) {
    super(errorMessage);
  }

}
