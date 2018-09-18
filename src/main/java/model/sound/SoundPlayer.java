package model.sound;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer implements ISoundPlayer {

  private Clip clip;

  private boolean isPlaying = false;

  public void setClip(File file) {
    try {
      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
    } catch (Exception exception) {
      System.out.println(exception);;
    }
  }

  @Override
  public void play() {
    if (clip == null) {
      return;
    }
    clip.start();
    isPlaying = true;
  }

  @Override
  public void loop() {
    if (clip == null) {
      return;
    }
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    isPlaying = true;
  }

  @Override
  public void stop() {
    if (clip == null) {
      return;
    }
    clip.stop();
    isPlaying = false;
  }

  @Override
  public boolean isPlaying() {
    return isPlaying;
  }
}
