/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.sound;

import java.io.File;

public interface ISoundPlayer {

  public void setClip(File file);

  public boolean isPlaying();

  public void play();

  public void loop();

  public void stop();

}
