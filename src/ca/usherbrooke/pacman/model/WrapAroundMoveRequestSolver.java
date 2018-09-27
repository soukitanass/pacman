package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class WrapAroundMoveRequestSolver {
  private Level level;

  public WrapAroundMoveRequestSolver(Level level) {
    this.level = level;
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
}
