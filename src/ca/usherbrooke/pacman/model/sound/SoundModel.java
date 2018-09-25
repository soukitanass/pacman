package ca.usherbrooke.pacman.model.sound;

import java.io.File;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class SoundModel extends Observer implements ISoundModel {
  private ISoundPlayer actionSoundPlayer = new SoundPlayer();
  private ISoundPlayer backgroundSoundPlayer = new SoundPlayer();
  private SoundFactory soundFactory = new SoundFactory();
  private boolean isMuted;

  public SoundModel(IGameModel subject) {
    this.subject = subject;
    this.subject.attach(this);
    playSound(backgroundSoundPlayer, Sound.SIREN, true);
  }

  @Override
  public boolean isMuted() {
    return isMuted;
  }

  @Override
  public void unmute() {
    isMuted = false;
    playSound(backgroundSoundPlayer, Sound.SIREN, true);
  }

  @Override
  public void mute() {
    isMuted = true;
    actionSoundPlayer.stop();
    backgroundSoundPlayer.stop();
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
  public void consumingPacGums() {
    if (isMuted) {
      return;
    }

    playSound(actionSoundPlayer, Sound.CHOMP_SOUND, true);
  }

  @Override
  public void consumingGhost() {
    if (isMuted) {
      return;
    }

    playSound(actionSoundPlayer, Sound.EAT_GHOST_SOUND, false);
  }

  @Override
  public void consumingFruit() {
    if (isMuted) {
      return;
    }

    playSound(actionSoundPlayer, Sound.EAT_FRUIT_SOUND, false);
  }

  @Override
  public void movingToEmptySpace() {
    if (actionSoundPlayer.isPlaying()) {
      actionSoundPlayer.stop();
    }
  }

  private void playSound(ISoundPlayer soundPlayer, Sound sound, boolean looping) {
    File soundFile;
    try {
      soundFile = soundFactory.getFile(sound);
      if (soundPlayer.isPlaying()) {
        return;
      }
      soundPlayer.setClip(soundFile);
      if (looping) {
        soundPlayer.loop();
      } else {
        soundPlayer.play();
      }
    } catch (Exception exception) {
      WarningDialog.display("Error while opening sound file. ", exception);
    }
  }

}
