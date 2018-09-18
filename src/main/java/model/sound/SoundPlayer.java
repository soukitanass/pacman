package model.sound;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer implements ISoundPlayer {

  private Clip clip;

  public SoundPlayer(File file) {
    try {
      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
    } catch (Exception exception) {
      System.out.println(exception);;
    }
  }

  @Override
  public void play() {
    clip.start();
  }

  @Override
  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  @Override
  public void stop() {
    clip.stop();
  }
}
