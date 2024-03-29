/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.sound;

public interface ISoundModel {
  boolean isSoundMuted();

  boolean isMusicMuted();
  
  void toggleSound();

  void setSoundVolumeChanged(int volume);

  void setMusicVolumeChanged(int volume);

  void unmuteMusic();

  void muteMusic();

  void unmuteSound();

  void muteSound();
}
