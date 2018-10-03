package ca.usherbrooke.pacman.model.sound;

public interface ISoundModel {
  boolean isMuted();

  void unmute();

  void mute();

  void toggleSound();

  void setSoundVolumeChanged(int volume);

  void setMusicVolumeChanged(int volume);
}
