package ca.usherbrooke.pacman.game;

public interface IGame {
  void update(long currentTime);
  void update();

  boolean isRunning();

  void setRunning(boolean isRunning);
}
