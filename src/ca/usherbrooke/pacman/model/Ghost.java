package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ghost implements GameObject {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("speed")
  @Expose
  private Integer speed;

  @SerializedName("start_pos")
  @Expose
  private Position position;
  private Direction direction;

  private Direction desiredDirection;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSpeed() {
    return speed;
  }

  public void setSpeed(Integer speed) {
    this.speed = speed;
  }

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
