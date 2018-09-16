package model;

public class MovementManager {

  private final Level level;
  private final PacMan pacman;
  private IMoveValidator moveValidator;

  public MovementManager(Level level, PacMan pacman, IMoveValidator moveValidator) {
    this.level = level;
    this.pacman = pacman;
    this.moveValidator = moveValidator;
  }

  public void updatePacmanPosition() {
    MoveRequest moveRequest = new MoveRequest(pacman.getPosition(), pacman.getDirection());
    if (!moveValidator.isValid(moveRequest)) {
      return;
    }
    final Position targetPosition = moveValidator.getTargetPosition(moveRequest);
    pacman.setPosition(targetPosition);
  }

  public void setPacmanDirection(Direction direction) {
    MoveRequest moveRequest = new MoveRequest(pacman.getPosition(), direction);
    if (!moveValidator.isValid(moveRequest)) {
      return;
    }
    pacman.setDirection(direction);
  }
}
