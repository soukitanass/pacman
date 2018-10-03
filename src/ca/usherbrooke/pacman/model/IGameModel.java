package ca.usherbrooke.pacman.model;

//import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.sound.Observer;

public interface IGameModel {
  void update();

  void loadLevels(String levelsPath);

  Level getCurrentLevel();

  int getCurrentGameFrame();

  void pause();

  void unpause();

  boolean isPaused();

  void setManuallyPaused(boolean isManuallyPaused);

  boolean isManuallyPaused();

  void togglePause(boolean isManuallyPaused);

  void quit();

  boolean isRunning();

  void setRunning(boolean isRunning);

  PacMan getPacman();

  void setDirection(IHasDesiredDirection gameObject, Direction direction);
    //  throws GameObjectCannotChangeDirectionException;

  void attach(Observer observer);

  void onInterruption();

  void consumingPacGums();

  void movingToEmptySpace();

  boolean isLevelCompleted();

  int getCurrentLevelIndex();

  public void setGameOver();

  public boolean isGameOver();

  boolean isGameCompleted();

  GameState getGameState();

  void setGameState(GameState gameState);

  void stopPhysicsThread();

  void consumingGhost();

}
