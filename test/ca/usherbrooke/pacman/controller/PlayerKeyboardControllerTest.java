package ca.usherbrooke.pacman.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.panel.LevelPanel;

public class PlayerKeyboardControllerTest {

  private PlayerKeyboardController playerKeyboardController;
  private IGameModel model;

  @Before
  public void setUp() throws Exception {
    final String LEVEL_PATH = "Level.json";
    model = new GameModel(Game.loadLevel(LEVEL_PATH));
    playerKeyboardController = new PlayerKeyboardController(model);
  }

  @Test
  public void EventsAreaddedToTheQueueTest() {
    LevelPanel levelPanel = new LevelPanel(model);
    KeyEvent key = new KeyEvent(levelPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
        KeyEvent.VK_P, 'P');
    playerKeyboardController.keyPressed(key);
    assertFalse(playerKeyboardController.getCommands().isEmpty());
    playerKeyboardController.update();
    assertTrue(playerKeyboardController.getCommands().isEmpty());


  }

}
