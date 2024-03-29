package ca.usherbrooke.pacman.mocktests;

import static org.junit.Assert.assertEquals;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.view.GameView;

public class DisplacementPacManMockTest {

  private GameModel model;
  private MockTestController mockTestController;
  private GameView view;
  private final int sleepTimeBetweenActionsMilliseconds = 1500;

  @Before
  public void setUp() throws AWTException, InterruptedException {
    Game game = new Game();
    game.initialize("Level.json");
    model = game.getModel();
    view = game.getView();
    mockTestController = new MockTestController(view, sleepTimeBetweenActionsMilliseconds);
    model.setGameState(GameState.GAME);
    model.update();
    game.start();
    Thread.sleep(sleepTimeBetweenActionsMilliseconds);
    assertEquals(GameState.GAME, model.getGameState());
  }

  @After
  public void tearDown() {
    mockTestController.close();
  }

  @Test(timeout = 30000)
  public void movingPacManRight() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_RIGHT);
    while (pacman.getDirection() != Direction.RIGHT);
  }

  @Test(timeout = 30000)
  public void movingPacManLeft() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    pacman.setDirection(Direction.RIGHT);
    mockTestController.tapKey(KeyEvent.VK_LEFT);
    while (pacman.getDirection() != Direction.LEFT);
  }

  @Test(timeout = 30000)
  public void movingPacManUp() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_UP);
    while (pacman.getDirection() != Direction.UP);
  }

  @Test(timeout = 30000)
  public void movingPacManDown() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_DOWN);
    while (pacman.getDirection() != Direction.DOWN);
  }
}
