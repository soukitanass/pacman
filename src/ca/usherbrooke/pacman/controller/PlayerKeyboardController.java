package ca.usherbrooke.pacman.controller;

import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;

public class PlayerKeyboardController implements IGameController, KeyListener {
  private final IGameModel model;

  public PlayerKeyboardController(IGameModel model, IGameView view) {
    this.model = model;
    view.getCanvas().setPausePanel();
  }

  @Override
  public void update() {
    // Do not remove!
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Do not remove!
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getKeyCode()) {
      case VK_P:
        model.togglePause(true);
        break;
      case VK_Q:
        model.quit();
        break;
      case KeyEvent.VK_RIGHT:
        model.setDirection(model.getPacman(), Direction.RIGHT);
        break;
      case KeyEvent.VK_LEFT:
        model.setDirection(model.getPacman(), Direction.LEFT);
        break;
      case KeyEvent.VK_UP:
        model.setDirection(model.getPacman(), Direction.UP);
        break;
      case KeyEvent.VK_DOWN:
        model.setDirection(model.getPacman(), Direction.DOWN);
        break;
      case KeyEvent.VK_ESCAPE:
        model.pause();
        model.setManuallyPaused(true);
        model.setGameState(GameState.GAME_MENU);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Do not remove!
  }
}
