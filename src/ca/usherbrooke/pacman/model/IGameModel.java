package ca.usherbrooke.pacman.model;

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

  void attach(Observer observer);

  void onLevelCompleted();

  void consumingPacGums();

  void movingToEmptySpace();

  boolean isLevelCompleted();

  int getCurrentLevelIndex();

  public void setGameOver();

  public boolean isGameOver();
}
