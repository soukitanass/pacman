package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class GameObject implements IHasDesiredDirection, IHasPosition, IHasDirection {

  @SerializedName("start_pos")
  @Expose
  private Position position;
  private Direction direction;

  private Direction desiredDirection;

  @Override
  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public void setDirection(Direction direction) {
    this.direction = direction;
    setDesiredDirection(direction);
  }

  @Override
  public Direction getDirection() {
    return this.direction;
  }

  @Override
  public void setDesiredDirection(Direction direction) {
    desiredDirection = direction;
  }

  @Override
  public Direction getDesiredDirection() {
    return desiredDirection;
  }
}
