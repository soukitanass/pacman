package model.sound;

public interface ISoundModel {
  boolean isMuted();

  void unmute();

  void mute();

  void toggleSound();
}
