package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ghost extends GameObject {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("speed")
  @Expose
  private Integer speed;

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

}
