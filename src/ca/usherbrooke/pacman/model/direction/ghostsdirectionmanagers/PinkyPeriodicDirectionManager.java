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
import ca.usherbrooke.pacman.model.direction.IDirectionGenerator;
import ca.usherbrooke.pacman.model.objects.IGameObject;

public class PinkyPeriodicDirectionManager extends PeriodicGhostDirectionManager {

  public PinkyPeriodicDirectionManager(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IGameObject gameObject, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.ghost = gameObject;
    this.period = period;
  }

}
