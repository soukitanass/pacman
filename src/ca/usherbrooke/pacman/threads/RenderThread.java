package ca.usherbrooke.pacman.threads;

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
      new CircularQueue<Long>(NB_UPDATE_TIMES_TO_REMEMBER);

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
      final long currentTimeMilliseconds = timeGetter.getMilliseconds();
      if (isTimeToUpdate(currentTimeMilliseconds)) {
        final long timeSinceLastUpdateMilliseconds =
            currentTimeMilliseconds - lastUpdateTimeMilliseconds;
        timesBetweenUpdatesMilliseconds.add(Long.valueOf(timeSinceLastUpdateMilliseconds));
        lastUpdateTimeMilliseconds = currentTimeMilliseconds;
        view.setFps(getFps());
        view.update();
      }
      sleepUntilNextUpdate();
    }
    System.out.println("STOP - " + this.getName());
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
  public void onClosingView() {
    shouldRun = false;
  }

  public int getFps() {
    int sumTimeBetweenUpdatesMilliseconds = 0;
    for (Long timeBetweenUpdatesMilliseconds : timesBetweenUpdatesMilliseconds) {
      sumTimeBetweenUpdatesMilliseconds += timeBetweenUpdatesMilliseconds;
    }
    return (int) (timesBetweenUpdatesMilliseconds.size() * 1000.0
        / sumTimeBetweenUpdatesMilliseconds);
  }

}
