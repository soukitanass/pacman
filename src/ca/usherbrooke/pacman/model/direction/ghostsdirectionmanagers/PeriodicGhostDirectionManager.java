/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction.ghostsdirectionmanagers;

import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IDirectionGenerator;
import ca.usherbrooke.pacman.model.objects.IGameObject;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PeriodicGhostDirectionManager {
  private int period;
  private IGameObject ghost;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;
  private int updatesCounter;

  public PeriodicGhostDirectionManager(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IGameObject gameObject, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.ghost = gameObject;
    this.period = period;
  }

  public void update() {

    final Direction escapeFromPacmanDirection = getOverridenDirectionToEscapeInvinciblePacman();
    if (escapeFromPacmanDirection != null) {
      gameModel.setDirection(ghost, escapeFromPacmanDirection);
      return;
    }

    final Direction exitGhostRoomDirection = getOverridenDirectionToExistGhostRoom();
    if (exitGhostRoomDirection != null) {
      gameModel.setDirection(ghost, exitGhostRoomDirection);
      return;
    }

    final Direction overridenDirection = directionGenerator.getOverridenDirection();
    if (overridenDirection != null) {
      gameModel.setDirection(ghost, overridenDirection);
      return;
    }

    ++updatesCounter;
    if (period != updatesCounter) {
      return;
    }
    updatesCounter = 0;

    gameModel.setDirection(ghost, directionGenerator.get());
  }

  // Return direction for escaping pacman or null if the ghost is not escaping.
  // If null is returned, we should use a fallback method to choose a direction.
  private Direction getOverridenDirectionToEscapeInvinciblePacman() {
    Level level = gameModel.getCurrentLevel();
    PacMan pacman = level.getPacMan();
    if (!pacman.isInvincible()) {
      return null;
    }
    final Position pacmanPosition = pacman.getPosition();
    final Position ghostPosition = ghost.getPosition();
    final Direction escapeFromPacmanDirection =
        level.getDirectionIfInLineOfSight(pacmanPosition, ghostPosition);
    return escapeFromPacmanDirection;
  }

  private Direction getOverridenDirectionToExistGhostRoom() {
    Level level = gameModel.getCurrentLevel();
    Direction direction = null;
    if (level.isGhostRoom(ghost.getPosition())) {
      direction = directionGenerator.get();
    }
    return direction;
  }
}
