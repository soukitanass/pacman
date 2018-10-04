package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;

public class GameThreadTest {

  private GameThread gameThread;
  private IGameModel mockModel;
  private IGameView mockView;
  private IGameController mockController;
  private static final int SLEEP_TIME = 100;
  @Before
  public void setUp() throws Exception {
    final long modelUpdatePeriod = 2;
    mockModel = mock(IGameModel.class);
    mockView = mock(IGameView.class);
    mockController = mock(IGameController.class);
    List<IGameController> controllers = new ArrayList<IGameController>();
    controllers.add(mockController);
    gameThread = new GameThread(mockModel,controllers,modelUpdatePeriod);
    gameThread.setName("Game_Thread");
    gameThread.start();
    assertTrue("thread dead", gameThread.isAlive());
  }

  @After
  public void tearDown() {
    boolean expectedStop = true;
    boolean actualStop = gameThread.isAlive();
    if (actualStop) {
      gameThread.stopThread();
      try {
        gameThread.join(1500);
        actualStop = !gameThread.isAlive();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      assertEquals("Thread not stopped", expectedStop, actualStop);
    }
  }
  
 @Test
 public void test() {
   
 }
  
}
