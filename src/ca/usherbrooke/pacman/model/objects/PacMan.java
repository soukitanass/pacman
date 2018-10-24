/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class PacMan implements IGameObject {

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

  public PacMan(Position position) {
    this.position = position;
  }

  public PacMan(PacMan pacMan) {
    this.position = new Position(pacMan.getPosition().getX(), pacMan.getPosition().getY());
    this.direction = pacMan.getDirection();
    this.desiredDirection = pacMan.getDesiredDirection();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
