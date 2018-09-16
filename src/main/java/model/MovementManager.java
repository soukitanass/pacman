package model;

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
    if (moveValidator.isValid(desiredMoveRequest)) {
      pacman.setPosition(moveValidator.getTargetPosition(desiredMoveRequest));
      return;
    }

    MoveRequest fallbackMoveRequest = new MoveRequest(pacman.getPosition(), pacman.getDirection());
    if (moveValidator.isValid(fallbackMoveRequest)) {
      pacman.setPosition(moveValidator.getTargetPosition(fallbackMoveRequest));
    }

    setPacmanDirection(pacman.getDesiredDirection());
  }

  public void setPacmanDirection(Direction direction) {
    pacman.setDesiredDirection(direction);
    MoveRequest moveRequest = new MoveRequest(pacman.getPosition(), direction);
    if (!moveValidator.isValid(moveRequest)) {
      return;
    }
    pacman.setDirection(direction);
  }
}
