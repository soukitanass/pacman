package ca.usherbrooke.pacman.controller;

import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import java.awt.event.KeyEvent;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;


public class InputHandler implements ICommand{
  private final IGameModel model;
  
  public InputHandler(IGameModel model) {
    this.model = model;
  }

  @Override
  public void execute(KeyEvent keyEvent) {
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
        pauseGameInProgress();
        model.setGameState(GameState.GAME_MENU);
        break;
      default:
        break;
    }
  }
  private void pauseGameInProgress() {
    if (!model.isGameOver()) {
      model.pause();
      model.setManuallyPaused(true);
    }
  }
  
}
