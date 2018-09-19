package model;

import model.sound.Observer;

public interface IGameModel {
  void update();

  void loadLevels(String levelsPath);

  Level getCurrentLevel();

  int getCurrentGameFrame();

  void pause();

  void unpause();

  boolean isPaused();

  void togglePause();

  void quit();

  boolean isRunning();

  void setRunning(boolean isRunning);

  PacMan getPacman();

  void setPacmanDirection(Direction direction);

  public void attach(Observer observer);

  public void consumingPacGums();

  public void movingToEmptySpace();
}
