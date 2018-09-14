package view;

import java.awt.event.KeyListener;

public interface IGameView {
  void update();

  void addKeyListener(KeyListener keyListener);

  void display();

  void close();
}
