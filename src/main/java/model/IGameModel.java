package model;

public interface IGameModel {
  void update();

  void loadLevels(String LEVELS_PATH);

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
}
