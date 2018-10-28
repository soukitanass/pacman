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
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.exceptions.InvalidSoundException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class SoundModel extends Observer implements ISoundModel {

  private static final int INITIAL_VOLUME = 100;

  private ISoundPlayer actionSoundPlayer = new SoundPlayer();
  private ISoundPlayer backgroundSoundPlayer = new SoundPlayer();
  private SoundFactory soundFactory = new SoundFactory();
  private boolean isSoundMuted = false;
  private boolean isMusicMuted = false;
  private int musicVolume = INITIAL_VOLUME;
  private int soundVolume = INITIAL_VOLUME;

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
  public void unmuteMusic() {
    isMusicMuted = false;
    playMusic(backgroundSoundPlayer, Sound.SIREN, true);
  }

  @Override
  public void muteMusic() {
    isMusicMuted = true;
    backgroundSoundPlayer.stop();
  }

  @Override
  public void unmuteSound() {
    isSoundMuted = false;
  }

  @Override
  public void muteSound() {
    isSoundMuted = true;
    actionSoundPlayer.stop();
  }

  @Override
  public void setSoundVolumeChanged(int volume) {
    soundVolume = volume;
    actionSoundPlayer.setVolume(volume);
  }

  @Override
  public void setMusicVolumeChanged(int volume) {
    musicVolume = volume;
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
  public void startBackgroundMusic() {
    if (backgroundSoundPlayer.isPlaying()) {
      backgroundSoundPlayer.stop();
    }
    soundPlay(backgroundSoundPlayer, Sound.SIREN, true);
  }

  @Override
  public void startInvincibleMusic() {
    if (backgroundSoundPlayer.isPlaying()) {
      backgroundSoundPlayer.stop();
    }
    soundPlay(backgroundSoundPlayer, Sound.INTERMISSION, true);
  }

  @Override
  public void startGame() {
    soundPlay(actionSoundPlayer, Sound.BEGINNING_SOUND, false);
  }

  @Override
  public void consumingPacGums() {
    soundPlay(actionSoundPlayer, Sound.CHOMP_SOUND, false);
  }

  @Override
  public void pacmanKilled() {
    soundPlay(actionSoundPlayer, Sound.DEATH_SOUND, false);
  }

  @Override
  public void consumingGhost() {
    soundPlay(actionSoundPlayer, Sound.EAT_GHOST_SOUND, false);
  }

  @Override
  public void consumingFruit() {
    soundPlay(actionSoundPlayer, Sound.EAT_FRUIT_SOUND, false);
  }

  @Override
  public void movingToEmptySpace() {
    if (actionSoundPlayer.isPlaying()) {
      actionSoundPlayer.stop();
    }
    if (!backgroundSoundPlayer.isPlaying()) {
      playMusic(backgroundSoundPlayer, Sound.SIREN, true);
    }
  }

  private void playMusic(ISoundPlayer soundPlayer, Sound sound, boolean looping) {
    if (isMusicMuted()) {
      return;
    }
    playSound(soundPlayer, sound, looping, musicVolume);
  }

  private void soundPlay(ISoundPlayer soundPlayer, Sound sound, boolean looping) {
    if (isSoundMuted()) {
      return;
    }
    playSound(soundPlayer, sound, looping, soundVolume);
  }

  private void playSound(ISoundPlayer soundPlayer, Sound sound, boolean looping, int volume) {
    File soundFile = null;
    try {
      soundFile = soundFactory.getFile(sound);
    } catch (InvalidSoundException exception) {
      WarningDialog.display("Error while opening sound file. ", exception);
    }
    if (soundPlayer.isPlaying()) {
      return;
    }
    soundPlayer.setClip(soundFile);
    soundPlayer.setVolume(volume);
    if (looping) {
      soundPlayer.loop();
    } else {
      soundPlayer.play();
    }
  }
}
