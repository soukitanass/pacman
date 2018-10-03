package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.controller.ISoundController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.IGameView;

public class AudioThread extends Thread {

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
  
  public AudioThread(IGameModel model) {
    this.model = model;
    soundPlayer = new SoundModel(this.model);
    soundController = new SoundController(soundPlayer);
    
  }

  public void setStop() {
    isRunning = false;
  }
  
  public void run() {
    System.out.println("START - "+getName());
    isRunning = true;
    while(isRunning) {
      if(isSoundVolumeChanged()) {
        soundPlayer.setMusicVolumeChanged(musicVolume);
      }
      if(isMusicVolumeChanged()) {
        soundPlayer.setSoundVolumeChanged(soundVolume);
      }
      
      if(isMusicPlay()) {
        if(isMuted) {
          soundPlayer.mute();
        }else {
          soundPlayer.unmute();
        }
        soundPlay = false;
      }
      if(isSoundPlay()) {
        
      }
      try {
        Thread.sleep(THREAD_SLEEP);
      } catch (InterruptedException e) {}
    }
    System.out.println("STOP - "+getName());
  }

  public synchronized boolean isSoundVolumeChanged() {
    return soundVolumeChanged;
  }

  public synchronized void setSoundVolumeChanged(int volume) {
    this.soundVolumeChanged = true;
    this.soundVolume = volume;
  }

  public synchronized boolean isMusicVolumeChanged() {
    return musicVolumeChanged;
  }

  public synchronized void setMusicVolumeChanged(int volume) {
    this.musicVolumeChanged = true;
    this.musicVolume = volume;
  }

  public synchronized boolean isSoundPlay() {
    return soundPlay;
  }

  public synchronized void setSoundPlay(boolean isMuted) {
    this.soundPlay = true;
    this.isMuted = isMuted;
  }

  public synchronized boolean isMusicPlay() {
    return musicPlay;
  }

  public synchronized void setMusicPlay(boolean musicPlay) {
    this.musicPlay = musicPlay;
  }

  public void addKeyListenner(IGameView view2) {
    view2.addKeyListener(soundController);
    
  }
}
