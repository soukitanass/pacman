/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.sound;

import ca.usherbrooke.pacman.model.IGameModel;

public abstract class Observer {
  protected IGameModel subject;

  public abstract void startGame();

  public abstract void consumingPacGums();

  public abstract void pacmanKilled();

  public abstract void consumingFruit();

  public abstract void movingToEmptySpace();

  public abstract void onGameInterruption();

  public abstract void consumingGhost();

  public abstract void startInvisibleMusic();
  
  public abstract void startBackgroundMusic();
  
}
