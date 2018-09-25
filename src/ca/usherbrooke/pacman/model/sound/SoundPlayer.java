package ca.usherbrooke.pacman.model.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class SoundPlayer implements ISoundPlayer {

  private Clip clip;

  private boolean isPlaying = false;

  public void setClip(File file) {
    try {
      clip = AudioSystem.getClip();
      try {
        clip.open(AudioSystem.getAudioInputStream(file));

      } catch (IOException | UnsupportedAudioFileException exception) {
        WarningDialog.display("Error while playing the sound file. ", exception);
      }
    } catch (IllegalArgumentException | LineUnavailableException ignored) {
      clip = null;
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