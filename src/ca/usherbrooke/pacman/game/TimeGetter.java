package ca.usherbrooke.pacman.game;

public class TimeGetter implements ITimeGetter {

  @Override
  public long getMillis() {
    return System.currentTimeMillis();
  }

}
