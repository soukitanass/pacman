package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacMan extends GameObject {

  @SerializedName("id")
  @Expose
  private Integer id;

  public PacMan() {
    setDirection(Direction.LEFT);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
