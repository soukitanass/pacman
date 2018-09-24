package ca.usherbrooke.pacman.model.sound;

import java.io.File;

public interface ISoundPlayer {

  public void setClip(File file);

  public boolean isPlaying();

  public void play();

  public void loop();

  public void stop();

}
