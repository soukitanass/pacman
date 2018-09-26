package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.model.IGameModel;

public class GameView implements IGameView {
  private GameCanvas canvas;

  public GameView(IGameModel model) {
    canvas = new GameCanvas(model);
  }

  public void update() {
    this.canvas.repaint();
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
  }

  public GameCanvas getCanvas() {
    return canvas;
  }

  public void setCanvas(GameCanvas canvas) {
    this.canvas = canvas;
  }

}
