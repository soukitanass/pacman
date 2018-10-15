/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
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
