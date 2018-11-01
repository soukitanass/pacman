package ca.usherbrooke.pacman.mocktests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.threads.AudioThread;
import ca.usherbrooke.pacman.threads.RenderThread;
import ca.usherbrooke.pacman.view.GameView;

public class CollisionMocktest {

  private MockTestController mockTestController;
  private GameModel model;
  private GameView view;
  private final int sleepTimeBetweenActionsMilliseconds = 1000;
  private AudioThread audio;
  private RenderThread renderThread;

  public void setUp(String levelPath) throws AWTException, InterruptedException {
    Game game = new Game();
    game.initialize(levelPath);
    model = game.getModel();
    view = game.getView();
    audio = game.getAudioThread();
    renderThread = game.getRenderThread();
    mockTestController = new MockTestController(view, sleepTimeBetweenActionsMilliseconds);
    game.start();
    Thread.sleep(sleepTimeBetweenActionsMilliseconds);
    assertTrue(model.isRunning());
    mockTestController.clickStartOrResumeGame();
    assertEquals(GameState.GAME, model.getGameState());
  }

  @After
  public void tearDown() {
    mockTestController.close();
  }

  @Test(timeout = 30000)
  public void whenPacmanHitsGhostThenPacmanLosesALife() throws InterruptedException, AWTException {
    setUp("CollisionMockTestLevelNoSuperPacgum.json");
    assertEquals(3, model.getLives());
    mockTestController.tapKey(KeyEvent.VK_RIGHT);
    while (model.getLives() == 3) {
      Thread.sleep(50);
    }
    assertEquals(2, model.getLives());
    assertTrue(model.getScore() < 200);
  }

  @Test(timeout = 30000)
  public void whenInvinciblePacmanHitsGhostThenGhostIsKilled()
      throws InterruptedException, AWTException {
    setUp("CollisionMockTestLevelWithSuperPacgum.json");
    assertEquals(3, model.getLives());
    mockTestController.tapKey(KeyEvent.VK_RIGHT);
    while (model.getPacman().getGhostKillsSinceInvincible() == 0) {
      Thread.sleep(50);
    }
    assertEquals(3, model.getLives());
    assertTrue(model.getScore() > 200);
  }

  @Test(timeout = 30000)
  public void simultaneousCollisionWithSuperPacGumAndGhost()
      throws AWTException, InterruptedException {
    setUp("CollisionMockTestSimultaneousCollision.json");
    assertEquals(3, model.getLives());
    while (model.getPacman().getPosition().getX() != 8) {
      assertFalse(model.isPacmanDead());
      Thread.sleep(50);
    }
    assertEquals(3, model.getLives());
    assertTrue(model.getScore() > 200);
  }

}
