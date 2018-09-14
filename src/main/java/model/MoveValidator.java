package model;

public class MoveValidator implements IMoveValidator {
  private final Level level;

  public MoveValidator(Level level) {
    this.level = level;
  }

  @Override
  public boolean isValid(IMoveRequest moveRequest) {
    final Position targetPosition = getTargetPosition(moveRequest);
    final boolean isEmptySpace = !level.isWall(targetPosition);
    return isEmptySpace;
  }

  private static Position getTargetPosition(IMoveRequest moveRequest) {
    int targetX = moveRequest.getPosition().getX();
    int targetY = moveRequest.getPosition().getY();
    switch (moveRequest.getDirection()) {
      case LEFT:
        targetX--;
        break;
      case RIGHT:
        targetX++;
        break;
      case UP:
        targetY--;
        break;
      case DOWN:
        targetX++;
        break;
    }
    return new Position(targetX, targetY);
  }
}
