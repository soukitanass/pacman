package ca.usherbrooke.pacman.model.exceptions;

@SuppressWarnings("serial")
public class GameObjectCannotChangeDirectionException extends Exception {
  public GameObjectCannotChangeDirectionException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

}
