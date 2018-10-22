/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IHasDesiredDirection;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
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

  void startNewGame();

  int getLives();

  void setLives(int i);

  Integer getScore();

  void setScore(Integer i);

  boolean isPacmanDead();

  void setIsPacmanDead(boolean isPacmanDead);

}
