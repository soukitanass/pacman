package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.FakeTimeGetter;
import ca.usherbrooke.pacman.view.IGameView;

public class RenderThreadTest {

  final int UPDATE_PERIOD_MILLISECONDS = 10;
  private static final int WAIT_TIME_MILLISECONDS = 50;
  private static final int TEARDOWN_WAIT_TIME_MILLISECONDS = 500;
  FakeTimeGetter timeGetter;
  Thread masterThread;
  RenderThread renderThread;
  IGameView viewUpdateSpy = mock(IGameView.class);

  @Before
  public void setUp() {
    timeGetter = new FakeTimeGetter(0);
    renderThread = new RenderThread(viewUpdateSpy, UPDATE_PERIOD_MILLISECONDS, timeGetter);
    masterThread = new Thread(renderThread);
    masterThread.start();
  }

  @After
  public void tearDown() throws InterruptedException {
    renderThread.onClosingView();
    masterThread.join(TEARDOWN_WAIT_TIME_MILLISECONDS);
    assertFalse(masterThread.isAlive());
  }

  @Test
  public void threadStopsGraciously() {}

  @Test
  public void callUpdateAtUpdatePeriods() throws InterruptedException {
    verify(viewUpdateSpy, times(0)).update();
    timeGetter.setCurrentTime(10);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    verify(viewUpdateSpy, times(1)).update();
    timeGetter.setCurrentTime(20);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    verify(viewUpdateSpy, times(2)).update();
  }

  @Test
  public void doNotCallUpdateBeforeUpdatePeriods() throws InterruptedException {
    verify(viewUpdateSpy, times(0)).update();
    timeGetter.setCurrentTime(9);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    verify(viewUpdateSpy, times(0)).update();
  }

}
