package ca.usherbrooke.pacman.controller;

import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PeriodicDirectionController implements IGameController {

  private int updatesCounter;
  private int period;
  private IHasDesiredDirection controlledGameObject;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;

  public PeriodicDirectionController(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IHasDesiredDirection controlledGameObject, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.controlledGameObject = controlledGameObject;
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
      gameModel.setDirection(controlledGameObject, direction);
    } catch (GameObjectCannotChangeDirectionException exception) {
      WarningDialog.display("Failed to set game object's direction", exception);
    }
  }

}
