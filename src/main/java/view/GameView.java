package view;

import model.IGameModel;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameView implements IGameView {
  private final IGameModel model;
  private GameCanvas canvas;
  private ArrayList<KeyListener> keyListeners = new ArrayList<>();

  public GameView(IGameModel model) {
    this.model = model;
  }

  public void update() {
    if (this.canvas != null) {
      this.canvas.repaint();
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    keyListeners.add(keyListener);
  }

  @Override
  public void display() {
    this.canvas = new GameCanvas(model);
    this.canvas.repaint();
    for (KeyListener keyListener : keyListeners) {
      canvas.addKeyListener(keyListener);
    }
  }

  @Override
  public void close() {
    canvas.dispose();
  }
}
