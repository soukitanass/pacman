package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacMan {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("start_pos")
  @Expose
  private Position position;
  private Direction direction;
  private IMoveValidator moveValidator;

  public PacMan() {
    this.direction = Direction.LEFT;
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
  }

  public Direction getDirection() {
    return this.direction;
  }

  public void setMoveValidator(IMoveValidator moveValidator) {
    this.moveValidator = moveValidator;
  }
}
