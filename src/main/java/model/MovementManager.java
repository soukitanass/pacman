package model;

import model.exceptions.InvalidDirectionException;

public class MovementManager {

  private final PacMan pacman;
  private IMoveValidator moveValidator;

  public MovementManager(PacMan pacman, IMoveValidator moveValidator) {
    this.pacman = pacman;
    this.moveValidator = moveValidator;
  }

  public void updatePacmanPosition() {
    MoveRequest desiredMoveRequest =
        new MoveRequest(pacman.getPosition(), pacman.getDesiredDirection());
    try {
      if (moveValidator.isValid(desiredMoveRequest)) {
        pacman.setPosition(moveValidator.getTargetPosition(desiredMoveRequest));
        return;
      }
    } catch (InvalidDirectionException e) {
      System.out.println(e.toString());
    }

    MoveRequest fallbackMoveRequest = new MoveRequest(pacman.getPosition(), pacman.getDirection());
    try {
      if (moveValidator.isValid(fallbackMoveRequest)) {
        pacman.setPosition(moveValidator.getTargetPosition(fallbackMoveRequest));
      }
    } catch (InvalidDirectionException e) {
      System.out.println(e.toString());
    }

    setPacmanDirection(pacman.getDesiredDirection());
  }

  public void setPacmanDirection(Direction direction) {
    pacman.setDesiredDirection(direction);
    MoveRequest moveRequest = new MoveRequest(pacman.getPosition(), direction);
    try {
      if (!moveValidator.isValid(moveRequest)) {
        return;
      }
    } catch (InvalidDirectionException e) {
      System.out.println(e.toString());
    }
    pacman.setDirection(direction);
  }
}
