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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import ca.usherbrooke.pacman.model.IGameModel;

public class PlayerKeyboardController implements IGameController, KeyListener {
  private InputHandler inputHandler;
  private Queue<KeyEvent> commands;

  public PlayerKeyboardController(IGameModel model) {
    inputHandler = new InputHandler(model);
    commands = new ConcurrentLinkedQueue<>();
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

  public synchronized Queue<KeyEvent> getCommands() {
    return commands;
  }

  public synchronized void setCommands(Queue<KeyEvent> commands) {
    this.commands = commands;
  }

}
