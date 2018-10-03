package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.controller.ISoundController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.IGameView;

public class AudioThread extends Thread implements CloseObserver{

  private static final int THREAD_SLEEP = 50;
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

  public void setStop() {
    System.out.println("stopping it");
    isRunning = false;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public void run() {
    System.out.println("START - " + getName());
    isRunning = true;
    while (isRunning) {
      try {
        System.out.println("je rentre dans le bloc");
        if (isSoundVolumeChanged()) {
          soundPlayer.setSoundVolumeChanged(soundVolume);
          System.out.println("le sound volume est changé " + soundVolume);
          soundVolumeChanged = false;
        }
        if (isMusicVolumeChanged()) {
          soundPlayer.setMusicVolumeChanged(musicVolume);
          System.out.println("le sound volume est changé " + musicVolume);
          musicVolumeChanged = false;
        }

        if (isMusicPlay()) {
          if (isMuted) {
            soundPlayer.mute();
            System.out.println("le son est mute");
          } else {
            soundPlayer.unmute();
            System.out.println("le son est on");
          }

          musicPlay = false;
        }
        if (isSoundPlay()) {
          if (isMuted) {
            soundPlayer.mute();
            System.out.println("le son est mute");
          } else {
            soundPlayer.unmute();
            System.out.println("le son est on");
          }
          soundPlay = false;
        }
        Thread.sleep(THREAD_SLEEP);
        synchronized (lock) {
          System.out.println("im waiting ");
          lock.wait();

        }


      } catch (InterruptedException e) {
      }
    }
    System.out.println("STOP - " + getName());
  }

  public synchronized boolean isSoundVolumeChanged() {
    return soundVolumeChanged;
  }

  public void setSoundVolumeChanged(int volume) {
    this.soundVolumeChanged = true;
    this.soundVolume = volume;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public boolean isMusicVolumeChanged() {
    return musicVolumeChanged;
  }

  public void setMusicVolumeChanged(int volume) {
    this.musicVolumeChanged = true;
    this.musicVolume = volume;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public boolean isSoundPlay() {
    return soundPlay;
  }

  public void setSoundPlay(boolean isMuted) {
    this.soundPlay = true;
    this.isMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public boolean isMusicPlay() {
    return musicPlay;
  }

  public void setMusicPlay(boolean isMuted) {
    this.musicPlay = true;
    this.isMuted = isMuted;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  public void addKeyListenner(IGameView view2) {
    view2.addKeyListener(soundController);

  }

  @Override
  public void onClosingView() {
    isRunning = false;
    synchronized (lock) {
      lock.notifyAll();
    }
    
  }
}
