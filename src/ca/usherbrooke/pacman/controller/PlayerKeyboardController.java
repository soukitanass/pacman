/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;

public class PlayerKeyboardController implements IGameController, KeyListener {
  private InputHandler inputHandler;
  private List<KeyEvent> commands;

  public PlayerKeyboardController(IGameModel model, IGameView view) {
    inputHandler = new InputHandler(model);
    view.getCanvas().setPausePanel();
    commands = new ArrayList<KeyEvent>();
  }

  @Override
  public void update() {
    for (KeyEvent keyEvent : commands) {
      inputHandler.execute(keyEvent);
    }
    commands.clear();
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    commands.add(keyEvent);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // do not remove

  }

  @Override
  public void keyTyped(KeyEvent e) {
    // do not remove
  }

}
