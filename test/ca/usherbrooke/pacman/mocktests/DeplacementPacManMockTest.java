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

public class DeplacementPacManMockTest {

  private GameModel model;
  private MockTestController mockTestController;
  private GameView view;
  private final int sleepTimeBetweenActionsMilliseconds = 1000;

  @Before
  public void setUp() throws AWTException, InterruptedException {
    Game game = new Game();
    game.initialize();
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

  @Test
  public void movingPacManToTheRight() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_RIGHT);
    assertEquals(Direction.RIGHT, pacman.getDirection());
  }

  @Test
  public void movingPacManToTheLeft() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    pacman.setDirection(Direction.RIGHT);
    mockTestController.tapKey(KeyEvent.VK_LEFT);
    assertEquals(Direction.LEFT, pacman.getDirection());
  }

  @Test
  public void movingPacManToTheUp() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_UP);
    assertEquals(Direction.UP, pacman.getDirection());
  }

  @Test
  public void movingPacManToTheDown() throws InterruptedException {
    PacMan pacman = model.getCurrentLevel().getPacMan();
    mockTestController.tapKey(KeyEvent.VK_DOWN);
    assertEquals(Direction.DOWN, pacman.getDirection());
  }
}
