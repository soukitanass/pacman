package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;

public class GameView implements IGameView {
  private GameCanvas canvas;
  private IGameModel model;
  private List<CloseObserver> closeObservers = new ArrayList<>();

  public GameView(IGameModel model, int ghostSpriteTogglePeriod, int pacmanSpriteTogglePeriod) {
    this.model = model;
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

}
