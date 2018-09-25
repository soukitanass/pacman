package ca.usherbrooke.pacman.game;

public interface IGame {
  void update(long currentTime);

  boolean isRunning();

  void setRunning(boolean isRunning);
}
