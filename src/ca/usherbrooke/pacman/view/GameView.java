/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.panel.FpsOptionListener;

public class GameView implements IGameView {
  private GameCanvas canvas;
  private IGameModel model;
  private List<CloseObserver> closeObservers = new ArrayList<>();
  private boolean isFpsEnabled = false;

  public GameView(IGameModel model, int ghostSpriteTogglePeriod, int pacmanSpriteTogglePeriod,
      FpsOptionListener fpsOptionListener) {
    this.model = model;
    canvas =
        new GameCanvas(model, ghostSpriteTogglePeriod, pacmanSpriteTogglePeriod, fpsOptionListener);
  }

  public void update() {
    canvas.setFpsEnabled(isFpsEnabled);
    if (!model.isGameCompleted()) {
      canvas.repaint();
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    canvas.addKeyListener(keyListener);
  }

  @Override
  public void display() {
    canvas.repaint();
  }

  @Override
  public void close() {
    notifyClosing();
    canvas.dispose();
  }

  private void notifyClosing() {
    for (CloseObserver closeObserver : closeObservers) {
      closeObserver.onClosingView();
    }
  }

  public GameCanvas getCanvas() {
    return canvas;
  }

  public void setCanvas(GameCanvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void addCloseObserver(CloseObserver closeObserver) {
    closeObservers.add(closeObserver);
  }

  @Override
  public void setFps(int fps) {
    canvas.setFps(fps);
  }

  @Override
  public void setFpsEnabled(boolean isFpsEnabled) {
    this.isFpsEnabled = isFpsEnabled;

  }

}
