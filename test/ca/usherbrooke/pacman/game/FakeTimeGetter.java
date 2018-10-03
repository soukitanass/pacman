package ca.usherbrooke.pacman.game;

public class FakeTimeGetter implements ITimeGetter {

  private long currentTimeMilliseconds;

  public FakeTimeGetter(int currentTimeMilliseconds) {
    setCurrentTime(currentTimeMilliseconds);
  }

  @Override
  public long getMilliseconds() {
    return currentTimeMilliseconds;
  }

  public void setCurrentTime(long currentTimeMilliseconds) {
    this.currentTimeMilliseconds = currentTimeMilliseconds;
  }

}
