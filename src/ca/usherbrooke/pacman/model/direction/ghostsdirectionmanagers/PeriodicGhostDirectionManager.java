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

public abstract class PeriodicGhostDirectionManager {
  protected int period;
  protected IGameObject ghost;
  protected IDirectionGenerator directionGenerator;
  protected IGameModel gameModel;
  private int updatesCounter;

  public void update() {
    ++updatesCounter;
    if (period != updatesCounter) {
      return;
    }
    updatesCounter = 0;
    final Direction escapeFromPacmanDirection = getOverridenDirectionToEscapeInvinciblePacman();
    if (escapeFromPacmanDirection != null) {
      gameModel.setDirection(ghost, escapeFromPacmanDirection);
      return;
    }
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
}
