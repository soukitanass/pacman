package ca.usherbrooke.pacman.controller;

import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;

public class PeriodicDirectionController implements IGameController {

  private int updatesCounter;
  private int period;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;

  public PeriodicDirectionController(IGameModel gameModel, IDirectionGenerator directionGenerator,
      int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.period = period;
  }

  public void update() {
    ++updatesCounter;

    for (Ghost ghost : gameModel.getCurrentLevel().getGhosts()) {
      if (period != updatesCounter) {
        return;
      }
      Direction direction = directionGenerator.get();
      gameModel.setDirection(ghost, direction);
    }

    updatesCounter = 0;
  }
}
