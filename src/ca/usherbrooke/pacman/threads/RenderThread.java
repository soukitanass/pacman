package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.game.ITimeGetter;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class RenderThread implements Runnable, CloseObserver {
  private static final String NAME = "RenderThread";
  private volatile boolean shouldRun = true;
  private long lastUpdateTimeMilliseconds;
  private int updatePeriodMilliseconds;
  private IGameView view;
  private ITimeGetter timeGetter;

  public RenderThread(IGameView view, int updatePeriodMilliseconds, ITimeGetter timeGetter) {
    this.view = view;
    this.updatePeriodMilliseconds = updatePeriodMilliseconds;
    this.timeGetter = timeGetter;
    lastUpdateTimeMilliseconds = timeGetter.getMillis();
  }

  @Override
  @SuppressWarnings("squid:S106")
  public void run() {
    System.out.println("START - " + this.getName());
    while (shouldRun) {
      final long currentTimeMillis = timeGetter.getMillis();
      if (isTimeToUpdate(currentTimeMillis)) {
        lastUpdateTimeMilliseconds = currentTimeMillis;
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
        timeGetter.getMillis() - lastUpdateTimeMilliseconds;
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

}
