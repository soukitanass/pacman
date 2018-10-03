package ca.usherbrooke.pacman.game;

public class TimeGetter implements ITimeGetter {

  @Override
  public long getMilliseconds() {
    return System.currentTimeMillis();
  }

}
