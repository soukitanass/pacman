/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class PacMan implements IGameObject, IHasInvincibilityStatus {

  @SerializedName("start_pos")
  @Expose
  private Position position;
  private Direction direction;
  private Direction desiredDirection;
  private boolean isInvincible;
  private int ghostKillsSinceInvincible;

  public PacMan() {
    isInvincible = false;
    setDirection(Direction.LEFT);
  }

  public PacMan(Position position) {
    this.position = position;
  }

  public PacMan(PacMan pacMan) {
    this.position = new Position(pacMan.getPosition().getX(), pacMan.getPosition().getY());
    this.direction = pacMan.getDirection();
    this.desiredDirection = pacMan.getDesiredDirection();
    this.isInvincible = pacMan.isInvincible();
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

  @Override
  public boolean isInvincible() {
    return isInvincible;
  }

  public void setIsInvincible(final boolean isInvincible) {
    if (!isInvincible) {
      ghostKillsSinceInvincible = 0;
    }
    this.isInvincible = isInvincible;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object other) {
    return EqualsBuilder.reflectionEquals(this, other);
  }

  public int getGhostKillsSinceInvincible() {
    return ghostKillsSinceInvincible;
  }

  public void setGhostKillsSinceInvincible(int ghostKillsSinceInvincible) {
    this.ghostKillsSinceInvincible = ghostKillsSinceInvincible;
  }

}
