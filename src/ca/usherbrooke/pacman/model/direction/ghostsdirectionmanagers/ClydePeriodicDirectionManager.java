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

public class ClydePeriodicDirectionManager implements IPeriodicDirectionManager {

  private int updatesCounter;
  private int period;
  private IGameObject gameObject;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;

  public ClydePeriodicDirectionManager(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IGameObject gameObject, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.gameObject = gameObject;
    this.period = period;
  }

  @Override
  public void update() {
    ++updatesCounter;
    if (period != updatesCounter) {
      return;
    }
    updatesCounter = 0;
    updatePosition();
  }

  private void updatePosition() {
    Level level = gameModel.getCurrentLevel();
    PacMan pacman = level.getPacMan();
    if (pacman.isInvincible()) {
      final Position pacmanPosition = pacman.getPosition();
      final Position ghostPosition = gameObject.getPosition();
      final Direction escapeFromPacmanDirection =
          level.getDirectionIfInLineOfSight(pacmanPosition, ghostPosition);
      if (escapeFromPacmanDirection != null) {
        gameModel.setDirection(gameObject, escapeFromPacmanDirection);
        return;
      }
    }
    gameModel.setDirection(gameObject, directionGenerator.get());
  }

}
