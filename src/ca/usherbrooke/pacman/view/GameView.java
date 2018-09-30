package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameView implements IGameView {
  private static final String NAME = "RenderThread";
  private GameCanvas canvas;
  private IGameModel model;
  private boolean shouldRun = false;
  private long lastUpdateTimeMilliseconds;
  private int updatePeriodMilliseconds;

  public GameView(IGameModel model, int ghostSpriteTogglePeriod, int pacmanSpriteTogglePeriod,
      int updatePeriodMilliseconds) {
    lastUpdateTimeMilliseconds = System.currentTimeMillis();
    this.model = model;
    this.updatePeriodMilliseconds = updatePeriodMilliseconds;
    canvas = new GameCanvas(model, ghostSpriteTogglePeriod, pacmanSpriteTogglePeriod);
  }

  public void update() {
    if (!model.isGameCompleted()) {
      this.canvas.repaint();
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    canvas.addKeyListener(keyListener);
  }

  @Override
  public void display() {
    this.canvas.repaint();
  }

  @Override
  public void close() {
    canvas.dispose();
    shouldRun = false;
  }

  public GameCanvas getCanvas() {
    return canvas;
  }

  public void setCanvas(GameCanvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void run() {
    System.out.println("START - " + this.getName());
    shouldRun = true;
    while (shouldRun) {
      final long currentTimeMillis = System.currentTimeMillis();
      if (isTimeToUpdate(currentTimeMillis)) {
        lastUpdateTimeMilliseconds = currentTimeMillis;
        update();
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
        System.currentTimeMillis() - lastUpdateTimeMilliseconds;
    final long timeUntilNextUpdateMilliseconds =
        updatePeriodMilliseconds - timeSinceLastUpdateMilliseconds;
    if (timeUntilNextUpdateMilliseconds <= 0) {
      return;
    }
    try {
      Thread.sleep(timeUntilNextUpdateMilliseconds);
    } catch (InterruptedException e) {
      WarningDialog.display("An error occured while waiting for the next frame update", e);
    }
  }

  private String getName() {
    return NAME;
  }

}
