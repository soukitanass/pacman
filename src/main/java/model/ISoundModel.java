package model;

public interface ISoundModel {
  boolean isMuted();

  void unmute();

  void mute();

  void toggleSound();
}
