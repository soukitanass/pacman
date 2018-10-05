/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/

/*******************************************************************************
 * FSP Code
 * RenderThread = (runStep->sleepUntilNextUpdate->RenderThread).
 * || Threads = (RenderThread).
 *
 ******************************************************************************/
package ca.usherbrooke.pacman.threads;

import java.util.concurrent.atomic.AtomicBoolean;
import ca.usherbrooke.pacman.game.ITimeGetter;
import ca.usherbrooke.pacman.utilities.CircularQueue;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class RenderThread implements Runnable, CloseObserver {
  private static final int NB_UPDATE_TIMES_TO_REMEMBER = 5;
  private static final String NAME = "RenderThread";
  private volatile boolean shouldRun = true;
  private long lastUpdateTimeMilliseconds;
  private int updatePeriodMilliseconds;
  private IGameView view;
  private ITimeGetter timeGetter;
  private CircularQueue<Long> timesBetweenUpdatesMilliseconds =
      new CircularQueue<>(NB_UPDATE_TIMES_TO_REMEMBER);
  private AtomicBoolean isFpsEnabled = new AtomicBoolean(false);

  public RenderThread(IGameView view, int updatePeriodMilliseconds, ITimeGetter timeGetter) {
    this.view = view;
    this.updatePeriodMilliseconds = updatePeriodMilliseconds;
    this.timeGetter = timeGetter;
    lastUpdateTimeMilliseconds = timeGetter.getMilliseconds();
  }

  @Override
  @SuppressWarnings("squid:S106")
  public void run() {
    System.out.println("START - " + this.getName());
    while (shouldRun) {
      runStep();
      sleepUntilNextUpdate();
    }
    System.out.println("STOP - " + this.getName());
  }

  private void runStep() {
    final long currentTimeMilliseconds = timeGetter.getMilliseconds();
    if (isTimeToUpdate(currentTimeMilliseconds)) {
      final long timeSinceLastUpdateMilliseconds =
          currentTimeMilliseconds - lastUpdateTimeMilliseconds;
      timesBetweenUpdatesMilliseconds.add(Long.valueOf(timeSinceLastUpdateMilliseconds));
      lastUpdateTimeMilliseconds = currentTimeMilliseconds;
      view.setFpsEnabled(isFpsEnabled.get());
      view.setFps(getFps());
      view.update();
    }
  }

  private boolean isTimeToUpdate(long currentTimeMilliseconds) {
    return currentTimeMilliseconds >= lastUpdateTimeMilliseconds + updatePeriodMilliseconds;
  }

  private void sleepUntilNextUpdate() {
    final long timeSinceLastUpdateMilliseconds =
        timeGetter.getMilliseconds() - lastUpdateTimeMilliseconds;
    final long timeUntilNextUpdateMilliseconds =
        updatePeriodMilliseconds - timeSinceLastUpdateMilliseconds;
    if (timeUntilNextUpdateMilliseconds <= 0) {
      return;
    }
    try {
      Thread.sleep(timeUntilNextUpdateMilliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      WarningDialog.display("An error occured while waiting for the next frame update", e);
    }
  }

  private String getName() {
    return NAME;
  }

  @Override
  public synchronized void onClosingView() {
    shouldRun = false;
  }

  public synchronized int getFps() {
    long sumTimeBetweenUpdatesMilliseconds = 0;
    for (Long timeBetweenUpdatesMilliseconds : timesBetweenUpdatesMilliseconds) {
      sumTimeBetweenUpdatesMilliseconds += timeBetweenUpdatesMilliseconds;
    }
    if (0 == sumTimeBetweenUpdatesMilliseconds) {
      return 0;
    }
    return (int) (timesBetweenUpdatesMilliseconds.size() * 1000.0
        / sumTimeBetweenUpdatesMilliseconds);
  }

  public synchronized void setFpsEnabled(boolean isFpsEnabled) {
    this.isFpsEnabled.set(isFpsEnabled);
  }

}
