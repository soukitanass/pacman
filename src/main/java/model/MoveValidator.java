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

  public Position getTargetPosition(IMoveRequest moveRequest) {
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
