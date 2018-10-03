package ca.usherbrooke.pacman.model;

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
    gameModel.setDirection(gameObject, direction);
  }
}
