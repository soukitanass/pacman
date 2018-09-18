package view;

import java.awt.event.KeyListener;
import model.IGameModel;

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
}
