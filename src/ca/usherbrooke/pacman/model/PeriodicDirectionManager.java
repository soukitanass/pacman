package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PeriodicDirectionManager {
  private int updatesCounter;
  private int period;
  private IHasDesiredDirection gameObject;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;

  public PeriodicDirectionManager(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IHasDesiredDirection gameObject, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.gameObject = gameObject;
    this.period = period;
  }

  public void update() {
    ++updatesCounter;
    if (period != updatesCounter) {
      return;
    }
    updatesCounter = 0;
    Direction direction = directionGenerator.get();
    try {
      gameModel.setDirection(gameObject, direction);
    } catch (GameObjectCannotChangeDirectionException exception) {
      WarningDialog.display("Failed to set game object's direction", exception);
    }
  }
}
