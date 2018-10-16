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
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class SoundPlayer implements ISoundPlayer {

  private Clip clip;
  private boolean isPlaying = false;

  @SuppressWarnings("squid:S1141")
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

  @Override
  public void setVolume(int volume) {
    if (clip == null) {
      return;
    }
      if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        if (gain > gainControl.getMaximum()) {
          gainControl.setValue(gainControl.getMaximum());
        } else {
          gainControl.setValue(gain);
        }
      } else {
        WarningDialog.display("No Volume controls available");
      }
  }

}
