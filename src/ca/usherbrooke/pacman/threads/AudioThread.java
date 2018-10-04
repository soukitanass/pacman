package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.controller.ISoundController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class AudioThread extends Thread implements CloseObserver {

  private static final int THREAD_SLEEP = 2;
  private volatile boolean isRunning = false;
  private ISoundModel soundPlayer;
  private ISoundController soundController;
  private final IGameModel model;

  private boolean soundVolumeChanged = false;
  private boolean musicVolumeChanged = false;
  private boolean soundPlay = false;
  private boolean musicPlay = false;
  private boolean isMuted = false;

  private int soundVolume;
  private int musicVolume;

  private Object lock = new Object();

  public AudioThread(IGameModel model) {
    this.model = model;
    soundPlayer = new SoundModel(this.model);
    soundController = new SoundController(soundPlayer);
  }

  public void run() {
    System.out.println("START - " + getName());
    isRunning = true;
    while (isRunning) {
      try {
        if (isSoundVolumeChanged()) {
          soundPlayer.setSoundVolumeChanged(soundVolume);
          soundVolumeChanged = false;
        }
        if (isMusicVolumeChanged()) {
          soundPlayer.setMusicVolumeChanged(musicVolume);
          musicVolumeChanged = false;
        }

        if (isMusicPlay() || isSoundPlay()) {
          if (isMuted) {
            soundPlayer.mute();
          } else {
            soundPlayer.unmute();
          }

          musicPlay = false;
          soundPlay = false;
        }
        Thread.sleep(THREAD_SLEEP);
        synchronized (lock) {
          lock.wait();
        }

      } catch (InterruptedException e) {
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

  public void setMusicVolume(int musicVolume) {
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

  public synchronized boolean isSoundPlay() {
    return soundPlay;
  }

  public synchronized void setSoundPlay(boolean isMuted) {
    this.soundPlay = true;
    this.isMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public synchronized boolean isMusicPlay() {
    return musicPlay;
  }

  public synchronized void setMusicPlay(boolean isMuted) {
    this.musicPlay = true;
    this.isMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public void addKeyListenner(IGameView view) {
    view.addKeyListener(soundController);
  }

  public void addCloseListenner(IGameView view) {
    view.addCloseObserver(this);
  }

  @Override
  public void onClosingView() {
    isRunning = false;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public void setStop() {
    isRunning = false;
    synchronized (lock) {
      lock.notifyAll();
    }
  }
}
