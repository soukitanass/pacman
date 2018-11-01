/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.game;

public interface IGame {
  void update();

  boolean isRunning();

  void setRunning(boolean isRunning);

  void stopRenderThread();

  void stopAudioThread();

  void stopThread();
}
