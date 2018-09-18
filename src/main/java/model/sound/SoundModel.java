package model.sound;

import java.io.File;
import model.IGameModel;

public class SoundModel extends Observer implements ISoundModel {
  private ISoundPlayer soundPlayer;
  private SoundFactory soundFactory = new SoundFactory();
  private boolean isMuted;

  public SoundModel(IGameModel subject) {
    this.subject = subject;
    this.subject.attach(this);
  }

  @Override
  public boolean isMuted() {
    return isMuted;
  }

  @Override
  public void unmute() {
    isMuted = false;
  }

  @Override
  public void mute() {
    isMuted = true;
  }

  @Override
  public void toggleSound() {
    if (isMuted()) {
      unmute();
    } else {
      mute();
    }
  }

  @Override
  public void update(Sound sound) {
    if (isMuted) {
      return;
    }

    File soundFile;
    try {
      soundFile = soundFactory.getFile(sound);
      soundPlayer = new SoundPlayer(soundFile);
      soundPlayer.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
