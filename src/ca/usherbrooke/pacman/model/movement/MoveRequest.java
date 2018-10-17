/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.movement;

import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.Position;

public class MoveRequest implements IMoveRequest {
  private final Direction direction;
  private final Position position;

  public MoveRequest(Position position, Direction direction) {
    this.position = position;
    this.direction = direction;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public Direction getDirection() {
    return direction;
  }
}
