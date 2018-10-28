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

public class BlinkyPeriodicDirectionManager implements IPeriodicDirectionManager {

  private int updatesCounter;
  private int period;
  private IGameObject gameObject;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;

  public BlinkyPeriodicDirectionManager(IGameModel gameModel,
      IDirectionGenerator directionGenerator, IGameObject gameObject, int period) {
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

    Direction direction = directionGenerator.get();
    gameModel.setDirection(gameObject, direction);
  }

}
