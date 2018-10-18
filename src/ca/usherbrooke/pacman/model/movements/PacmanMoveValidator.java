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

public class PacmanMoveValidator implements IMoveValidator {

  private Level level;

  public PacmanMoveValidator(Level level) {
    this.level = level;
  }

  @Override
  public boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException {
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition);
  }

  @Override
  public Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException {
    return new WrapAroundMoveRequestSolver(level).getTargetPosition(moveRequest);
  }

  @Override
  public boolean isDesiredDirectionValid(IMoveRequest moveRequest)
      throws InvalidDirectionException {
    final Position currentPosition = moveRequest.getPosition();
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition)
        && !level.isTunnel(currentPosition);
  }

}
