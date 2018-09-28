package ca.usherbrooke.pacman.controller;

import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PlayerKeyboardController implements IGameController, KeyListener {
  private final IGameModel model;
  private final IGameView view;

  public PlayerKeyboardController(IGameModel model, IGameView view) {
    this.model = model;
    this.view = view;
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
        try {
          model.setDirection(model.getPacman(), Direction.RIGHT);
        } catch (GameObjectCannotChangeDirectionException exception) {
          WarningDialog.display("Failed to set pacman's direction", exception);
        }
        break;
      case KeyEvent.VK_LEFT:
        try {
          model.setDirection(model.getPacman(), Direction.LEFT);
        } catch (GameObjectCannotChangeDirectionException e) {
          WarningDialog.display("Failed to set pacman's direction", e);
        }
        break;
      case KeyEvent.VK_UP:
        try {
          model.setDirection(model.getPacman(), Direction.UP);
        } catch (GameObjectCannotChangeDirectionException e) {
          WarningDialog.display("Failed to set pacman's direction", e);
        }
        break;
      case KeyEvent.VK_DOWN:
        try {
          model.setDirection(model.getPacman(), Direction.DOWN);
        } catch (GameObjectCannotChangeDirectionException e) {
          WarningDialog.display("Failed to set pacman's direction", e);
        }
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
