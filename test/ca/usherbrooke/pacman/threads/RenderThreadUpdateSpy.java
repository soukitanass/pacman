package ca.usherbrooke.pacman.threads;

import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.GameCanvas;
import ca.usherbrooke.pacman.view.IGameView;

public class RenderThreadUpdateSpy implements IGameView {

  private int updateCalls = 0;

  @Override
  public void update() {
    ++updateCalls;
  }

  public int getUpdateCalls() {
    return updateCalls;
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    // Dummy method
  }

  @Override
  public void display() {
    // Dummy method
  }

  @Override
  public void close() {
    // Dummy method
  }

  @Override
  public GameCanvas getCanvas() {
    // Dummy method
    return null;
  }

  @Override
  public void setCanvas(GameCanvas gameCanvas) {
    // Dummy method
  }

  @Override
  public void addCloseObserver(CloseObserver closeObserver) {
    // Dummy method
  }

}
