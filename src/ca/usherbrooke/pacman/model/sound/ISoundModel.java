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
