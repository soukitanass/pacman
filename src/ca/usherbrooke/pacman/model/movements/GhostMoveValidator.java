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
import ca.usherbrooke.pacman.model.objects.IHasInvincibilityStatus;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;

public class GhostMoveValidator implements IMoveValidator {

  private Level level;
  private IHasInvincibilityStatus hasInvincibilityStatus;

  public GhostMoveValidator(Level level, IHasInvincibilityStatus hasInvincibilityStatus) {
    this.level = level;
    this.hasInvincibilityStatus = hasInvincibilityStatus;
  }

  @Override
  public boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException {
    return isDirectionValid(moveRequest);
  }

  @Override
  public Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException {
    return new WrapAroundMoveRequestSolver(level).getTargetPosition(moveRequest);
  }

  @Override
  public boolean isDesiredDirectionValid(IMoveRequest moveRequest)
      throws InvalidDirectionException {
    return isDirectionValid(moveRequest);
  }

  private boolean isDirectionValid(IMoveRequest moveRequest) throws InvalidDirectionException {
    final Position currentPosition = moveRequest.getPosition();
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isTunnel(targetPosition)
        && !isUsingGhostGateTheWrongWay(currentPosition, targetPosition);
  }

  private boolean isUsingGhostGateTheWrongWay(Position currentPosition, Position targetPosition) {
    final boolean isGoingFromGhostGateToGhostRoom =
        level.isGhostGate(currentPosition) && level.isGhostRoom(targetPosition);
    final boolean isGoingFromAnythingButGhostRoomToGhostGate =
        !level.isGhostRoom(currentPosition) && level.isGhostGate(targetPosition);
    final boolean isGoingFromGhostRoomToGhostGateDuringInvincibilityMode =
        level.isGhostRoom(currentPosition) && level.isGhostGate(targetPosition)
            && hasInvincibilityStatus.isInvincible();
    return isGoingFromGhostGateToGhostRoom || isGoingFromAnythingButGhostRoomToGhostGate
        || isGoingFromGhostRoomToGhostGateDuringInvincibilityMode;
  }

}
