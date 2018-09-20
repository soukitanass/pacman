package model;

import model.Direction;

public class MoveRequest implements IMoveRequest {
  private final Direction direction;
  private final Position position;

  public MoveRequest(Position position, Direction direction) {
    this.position = position;
    this.direction = direction;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public Direction getDirection() {
    return direction;
  }
}
