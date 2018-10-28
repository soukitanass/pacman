package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.controller.ISoundController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.utilities.CloseObserver;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class AudioThread extends Thread implements CloseObserver {

  private static final int THREAD_SLEEP = 3;
  private volatile boolean isRunning = false;
  private ISoundModel soundModel;
  private ISoundController soundController;
  private final IGameModel model;

  private boolean soundVolumeChanged = false;
  private boolean musicVolumeChanged = false;
  private boolean soundPlay = false;
  private boolean musicPlay = false;

  private int soundVolume;
  private int musicVolume;

  private Object lock = new Object();
  private boolean isMusicMuted = false;
  private boolean isSoundMuted = false;

  public AudioThread(IGameModel model) {
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
        if (isSoundVolumeChanged()) {
          soundModel.setSoundVolumeChanged(soundVolume);
          soundVolumeChanged = false;
        }
        if (isMusicVolumeChanged()) {
          soundModel.setMusicVolumeChanged(musicVolume);
          musicVolumeChanged = false;
        }
        if (isMusicPlay()) {
          if (isMusicMuted) {
            soundModel.muteMusic();
          } else {
            soundModel.unmuteMusic();
          }
          setTheMusicPlay(false);
        }

        if (isSoundPlaying()) {
          if (isSoundMuted) {
            soundModel.muteSound();
          } else {
            soundModel.unmuteSound();
          }
          setTheSoundPlay(false);
        }
        Thread.sleep(THREAD_SLEEP);
        synchronized (lock) {
          lock.wait();
        }

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        WarningDialog.display("Error while interrupting " + this.getName(), e);
      }
    }
    System.out.println("STOP - " + getName());
  }

  public synchronized boolean isSoundVolumeChanged() {
    return soundVolumeChanged;
  }

  public synchronized void setSoundVolumeChanged(int volume) {
    this.soundVolumeChanged = true;
    this.soundVolume = volume;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public synchronized int getSoundVolume() {
    return soundVolume;
  }

  public synchronized void setSoundVolume(int soundVolume) {
    this.soundVolume = soundVolume;
  }

  public synchronized int getMusicVolume() {
    return musicVolume;
  }

  public synchronized void setMusicVolume(int musicVolume) {
    this.musicVolume = musicVolume;
  }

  public synchronized boolean isMusicVolumeChanged() {
    return musicVolumeChanged;
  }

  public synchronized void setMusicVolumeChanged(int volume) {
    this.musicVolumeChanged = true;
    this.musicVolume = volume;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public synchronized boolean isSoundPlaying() {
    return soundPlay;
  }

  public synchronized void setIsSoundPlaying(boolean isMuted) {
    this.soundPlay = true;
    this.isSoundMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public synchronized void setTheSoundPlay(boolean soundPlay) {
    this.soundPlay = soundPlay;
  }

  public synchronized boolean isMusicPlay() {
    return musicPlay;
  }

  public synchronized void setMusicPlay(boolean isMuted) {
    this.musicPlay = true;
    this.isMusicMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public synchronized void setTheMusicPlay(boolean musicPlay) {
    this.musicPlay = musicPlay;
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

  public synchronized void stopThread() {
    isRunning = false;
    synchronized (lock) {
      lock.notifyAll();
    }
  }
}
