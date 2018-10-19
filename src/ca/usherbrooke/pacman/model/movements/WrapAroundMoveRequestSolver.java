/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.movements;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;

public class WrapAroundMoveRequestSolver {
  private Level level;

  public WrapAroundMoveRequestSolver(Level level) {
    this.level = level;
  }

  public Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException {
    int targetX = moveRequest.getPosition().getX();
    int targetY = moveRequest.getPosition().getY();
    switch (moveRequest.getDirection()) {
      case LEFT:
        --targetX;
        break;
      case RIGHT:
        ++targetX;
        break;
      case UP:
        --targetY;
        break;
      case DOWN:
        ++targetY;
        break;
      default:
        throw new InvalidDirectionException("Invalid requested direction.");
    }
    Integer levelWidth = level.getWidth();
    Integer levelHeight = level.getHeight();
    if (targetX < 0) {
      targetX += levelWidth;
    }
    if (targetY < 0) {
      targetY += levelHeight;
    }
    if (targetX >= levelWidth) {
      targetX -= levelWidth;
    }
    if (targetY >= levelHeight) {
      targetY -= levelHeight;
    }
    return new Position(targetX, targetY);
  }
}
