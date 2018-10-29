package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;

public class GameThreadTest {

  private static final long SLEEP_TIME = 155;
  private static final int JOIN_TIME = 1500;

  private GameThread gameThread;
  private IGameModel mockModel;
  private IGameController mockController;
  private Thread mockViewThread;
  private IGameView mockView;

  @Before
  public void setUp() throws Exception {
    AudioThread mockAudioThread = mock(AudioThread.class);
    mockView = mock(IGameView.class);
    mockViewThread = mock(Thread.class);

    mockController = mock(PlayerKeyboardController.class);
    mockModel = mock(IGameModel.class);
    when(mockModel.isRunning()).thenReturn(true);

    List<IGameController> controllers = new ArrayList<>();
    controllers.add(mockController);

    gameThread = new GameThread(mockModel, mockViewThread, mockAudioThread, mockView, controllers);
    gameThread.setName("Game_Thread");
    gameThread.start();

    try {
      gameThread.join(100);
    } catch (InterruptedException e) {
      gameThread.interrupt();
    }

    assertTrue("Thread is not alive", gameThread.isAlive());
    verify(mockModel, times(1)).setRunning(true);
    verify(mockModel, times(1)).startNewGame();
    verify(mockViewThread, times(1)).start();
    verify(mockAudioThread, times(1)).start();
  }

  @After
  public void tearDown() {
    boolean isAlive = gameThread.isAlive();
    if (isAlive) {
      when(mockModel.isRunning()).thenReturn(false);
      gameThread.stopThread();
      try {
        gameThread.join(JOIN_TIME);
        isAlive = gameThread.isAlive();
      } catch (InterruptedException e) {
        gameThread.interrupt();
      }
      assertFalse("Thread is still running", isAlive);
    }
  }

  @Test
  public void callUpdateAtUpdatePeriods() throws InterruptedException {
    verify(mockModel, times(1)).update();
    verify(mockController, times(1)).update();
    Thread.sleep(SLEEP_TIME);
    verify(mockModel, times(2)).update();
    verify(mockController, times(2)).update();
    Thread.sleep(SLEEP_TIME);
    verify(mockModel, times(3)).update();
    verify(mockController, times(3)).update();
  }

  @Test
  public void closeView() {
    gameThread.stopViewThread();
    verify(mockView, times(1)).close();
  }

}
