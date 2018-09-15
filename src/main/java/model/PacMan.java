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

  public void updatePosition(int levelWidth, int levelHeight) {
    int x = position.getX();
    int y = position.getY();

    switch (this.direction) {
      case RIGHT:
        if (x < levelWidth) {
          x++;
        } else {
          x = 0;
        }
        break;
      case LEFT:
        if (x > 0) {
          x--;
        } else {
          x = levelWidth;
        }
        break;
      case UP:
        if (y > 0) {
          y--;
        } else {
          y = levelHeight;
        }
        break;
      case DOWN:
        if (y < levelHeight) {
          y++;
        } else {
          y = 0;
        }
        break;
      default:
        break;
    }
    this.setPosition(new Position(x, y));
  }
}
