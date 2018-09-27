package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class PacmanMoveValidator implements IMoveValidator {

  private Level level;

  public PacmanMoveValidator(Level level) {
    this.level = level;
  }

  @Override
  public boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException {
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition);
  }

  public Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException {
    int targetX = moveRequest.getPosition().getX();
    int targetY = moveRequest.getPosition().getY();
    switch (moveRequest.getDirection()) {
      case LEFT:
        --targetX;
        break;
      case RIGHT:
        ++targetX;
        break;
      case UP:
        --targetY;
        break;
      case DOWN:
        ++targetY;
        break;
      default:
        throw new InvalidDirectionException("Invalid requested direction.");
    }
    Integer levelWidth = level.getWidth();
    Integer levelHeight = level.getHeight();
    if (targetX < 0) {
      targetX += levelWidth;
    }
    if (targetY < 0) {
      targetY += levelHeight;
    }
    if (targetX >= levelWidth) {
      targetX -= levelWidth;
    }
    if (targetY >= levelHeight) {
      targetY -= levelHeight;
    }
    return new Position(targetX, targetY);
  }

  @Override
  public boolean isDesiredDirectionValid(IMoveRequest moveRequest)
      throws InvalidDirectionException {
    final Position currentPosition = moveRequest.getPosition();
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition)
        && !level.isTunnel(currentPosition);
  }

}
