package ca.usherbrooke.pacman.controller;

import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;

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
    gameModel.setDirection(controlledGameObject, direction);
  }

}
