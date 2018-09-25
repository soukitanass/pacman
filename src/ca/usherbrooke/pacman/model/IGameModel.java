package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.sound.Observer;

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

  void attach(Observer observer);

  void consumingPacGums();

  void movingToEmptySpace();
}