package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacMan implements IHasDesiredDirection {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("start_pos")
  @Expose
  private Position position;
  private Direction direction;

  private Direction desiredDirection;

  public PacMan() {
    setDirection(Direction.LEFT);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return this.position;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
    setDesiredDirection(direction);
  }

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
