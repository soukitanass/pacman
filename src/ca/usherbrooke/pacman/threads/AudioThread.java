package ca.usherbrooke.pacman.threads;

import java.util.concurrent.atomic.AtomicBoolean;
import ca.usherbrooke.pacman.controller.ISoundController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.utilities.CloseObserver;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class AudioThread extends Thread implements CloseObserver {

  private static final String NAME = "Audio_Thread";
  private static final int THREAD_SLEEP = 500;

  private final Object musicVolumeLock = new Object();
  private final Object soundVolumeLock = new Object();

  private volatile boolean isRunning = false;
  private ISoundModel soundModel;
  private ISoundController soundController;
  private final IGameModel model;

  private AtomicBoolean isSoundVolumeChanged = new AtomicBoolean(false);
  private AtomicBoolean isMusicVolumeChanged = new AtomicBoolean(false);
  private AtomicBoolean isSoundPlaying = new AtomicBoolean(false);
  private AtomicBoolean isMusicPlaying = new AtomicBoolean(false);

  private AtomicBoolean isMusicMuted = new AtomicBoolean(false);
  private AtomicBoolean isSoundMuted = new AtomicBoolean(false);

  private int soundVolume;
  private int musicVolume;

  public AudioThread(IGameModel model) {
    this.setName(NAME);
    this.model = model;
    soundModel = new SoundModel(this.model);
    soundController = new SoundController(soundModel);
  }

  @Override
  @SuppressWarnings("squid:S106")
  public void run() {
    System.out.println("START - " + getName());
    isRunning = true;

    while (isRunning) {
      try {
        if (isSoundVolumeChanged.get()) {
          soundModel.setSoundVolumeChanged(soundVolume);
          isSoundVolumeChanged.set(false);
        }
        if (isMusicVolumeChanged.get()) {
          soundModel.setMusicVolumeChanged(musicVolume);
          isMusicVolumeChanged.set(false);
        }

        if (isMusicPlaying.get()) {
          if (isMusicMuted.get()) {
            soundModel.muteMusic();
          } else {
            soundModel.unmuteMusic();
          }
          isMusicPlaying.set(false);
        }

        if (isSoundPlaying.get()) {
          if (isSoundMuted.get()) {
            soundModel.muteSound();
          } else {
            soundModel.unmuteSound();
          }
          isSoundPlaying.set(false);
        }
        Thread.sleep(THREAD_SLEEP);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        WarningDialog.display("Error while interrupting " + this.getName(), e);
      }
    }

    System.out.println("STOP - " + getName());
  }

  public synchronized void setSoundVolume(int soundVolume) {
    synchronized (soundVolumeLock) {
      this.soundVolume = soundVolume;
      isSoundVolumeChanged.set(true);
    }
  }

  public synchronized int getSoundVolume() {
    synchronized (soundVolumeLock) {
      return soundVolume;
    }
  }

  public synchronized void setMusicVolume(int musicVolume) {
    synchronized (musicVolumeLock) {
      this.musicVolume = musicVolume;
      isMusicVolumeChanged.set(true);
    }
  }

  public synchronized int getMusicVolume() {
    synchronized (musicVolumeLock) {
      return musicVolume;
    }
  }

  public synchronized void setIsSoundPlaying(boolean isMuted) {
    isSoundPlaying.set(true);
    isSoundMuted.set(isMuted);
  }

  public synchronized boolean isSoundPlaying() {
    return isSoundPlaying.get();
  }

  public synchronized void setTheSoundPlay(boolean soundPlay) {
    isSoundPlaying.set(soundPlay);
  }

  public synchronized void setMusicPlay(boolean isMuted) {
    isMusicPlaying.set(true);
    isMusicMuted.set(isMuted);
  }

  public synchronized boolean isMusicMuted() {
    return isMusicMuted.get();
  }

  public synchronized boolean isSoundMuted() {
    return isSoundMuted.get();
  }

  public synchronized ISoundController getSoundController() {
    return soundController;
  }

  public synchronized void setSoundController(ISoundController soundController) {
    this.soundController = soundController;
  }

  @Override
  public synchronized void onClosingView() {
    stopThread();
  }

  public void stopThread() {
    isRunning = false;
  }

}
