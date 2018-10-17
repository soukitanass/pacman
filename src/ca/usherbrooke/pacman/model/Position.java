/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Position {

  @SerializedName("x")
  @Expose
  private Integer x;
  @SerializedName("y")
  @Expose
  private Integer y;


  public Position() {

  }

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (other == null || getClass() != other.getClass())
      return false;
    Position position = (Position) other;
    return Objects.equals(getX(), position.getX()) && Objects.equals(getY(), position.getY());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY());
  }
}
