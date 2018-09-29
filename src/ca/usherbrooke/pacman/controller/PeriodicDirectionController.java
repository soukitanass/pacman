package ca.usherbrooke.pacman.controller;

import java.util.List;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.IGameObjectsLambda;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;

public class PeriodicDirectionController implements IGameController {

  private int updatesCounter;
  private int period;
  private IDirectionGenerator directionGenerator;
  private IGameModel gameModel;
  private IGameObjectsLambda gameObjectsLambda;

  public PeriodicDirectionController(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IGameObjectsLambda gameObjectsLambda, int period) {
    this.gameModel = gameModel;
    this.directionGenerator = directionGenerator;
    this.gameObjectsLambda = gameObjectsLambda;
    this.period = period;
  }

  public void update() {
    List<IHasDesiredDirection> gameObjects = gameObjectsLambda.getHasDesiredDirection();

    ++updatesCounter;

    for (IHasDesiredDirection gameObject : gameObjects) {
      if (period != updatesCounter) {
        return;
      }
      Direction direction = directionGenerator.get();
      gameModel.setDirection(gameObject, direction);
    }

    updatesCounter = 0;
  }
}
