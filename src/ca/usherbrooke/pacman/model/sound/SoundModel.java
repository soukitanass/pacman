package ca.usherbrooke.pacman.model.sound;

import java.io.File;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class SoundModel extends Observer implements ISoundModel {
  private ISoundPlayer actionSoundPlayer = new SoundPlayer();
  private ISoundPlayer backgroundSoundPlayer = new SoundPlayer();
  private SoundFactory soundFactory = new SoundFactory();
  private boolean isSoundMuted = false;
  private boolean isMusicMuted = false;

  public SoundModel(IGameModel subject) {
    this.subject = subject;
    this.subject.attach(this);
  }

  @Override
  public boolean isSoundMuted() {
    return isSoundMuted;
  }

  @Override
  public boolean isMusicMuted() {
    return isMusicMuted;
  }

  @Override
  public synchronized void unmuteMusic() {
    isMusicMuted = false;
    playSound(backgroundSoundPlayer, Sound.SIREN, true);
  }

  @Override
  public synchronized void muteMusic() {
    isMusicMuted = true;
    backgroundSoundPlayer.stop();
  }

  @Override
  public synchronized void unmuteSound() {
    isSoundMuted = false;
  }

  @Override
  public synchronized void muteSound() {
    isSoundMuted = true;
    actionSoundPlayer.stop();
  }

  @Override
  public void setSoundVolumeChanged(int volume) {
    actionSoundPlayer.setVolume(volume);
  }

  @Override
  public void setMusicVolumeChanged(int volume) {
    backgroundSoundPlayer.setVolume(volume);
  }

  @Override
  public synchronized void toggleSound() {
    if (isSoundMuted()) {
      unmuteSound();
    } else {
      muteSound();
    }
    if (isMusicMuted()) {
      unmuteMusic();
    } else {
      muteMusic();
    }
  }

  @Override
  public void onGameInterruption() {
    actionSoundPlayer.stop();
    backgroundSoundPlayer.stop();
  }

  @Override
  public void consumingPacGums() {
    if (!isSoundMuted()) {
      playSound(actionSoundPlayer, Sound.CHOMP_SOUND, true);
    }
  }

  @Override
  public void consumingGhost() {
    if (!isSoundMuted()) {
      playSound(actionSoundPlayer, Sound.EAT_GHOST_SOUND, false);
    }
  }

  @Override
  public void consumingFruit() {
    if (!isSoundMuted()) {
      playSound(actionSoundPlayer, Sound.EAT_FRUIT_SOUND, false);
    }
  }

  @Override
  public void movingToEmptySpace() {
    if (actionSoundPlayer.isPlaying()) {
      actionSoundPlayer.stop();
    }
    if (!backgroundSoundPlayer.isPlaying()) {
      playSound(backgroundSoundPlayer, Sound.SIREN, true);
    }
  }

  private void playSound(ISoundPlayer soundPlayer, Sound sound, boolean looping) {
    if (isMusicMuted()) {
      return;
    }
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
