package ca.usherbrooke.pacman.view;

import java.awt.event.KeyListener;

public interface IGameView {
  void update();

  void addKeyListener(KeyListener keyListener);

  void display();

  void close();

  GameCanvas getCanvas();

  void setCanvas(GameCanvas gameCanvas);

  void addCloseObserver(CloseObserver closeObserver);
}
