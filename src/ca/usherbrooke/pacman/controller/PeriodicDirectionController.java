package ca.usherbrooke.pacman.controller;

import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;

public class PeriodicDirectionController implements IGameController {

  private int updatesCounter;
  private int period;
  private IHasDesiredDirection someMovableObject;
  private IDirectionGenerator directionGenerator;

  public PeriodicDirectionController(IDirectionGenerator directionGenerator,
      IHasDesiredDirection someMovableObject, int period) {
    this.directionGenerator = directionGenerator;
    this.someMovableObject = someMovableObject;
    this.period = period;
  }

  public void update() {
    ++updatesCounter;
    if (period != updatesCounter) {
      return;
    }
    updatesCounter = 0;
    someMovableObject.setDesiredDirection(directionGenerator.get());
  }

}
