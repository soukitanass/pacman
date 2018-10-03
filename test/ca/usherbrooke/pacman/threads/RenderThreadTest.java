package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.FakeTimeGetter;

public class RenderThreadTest {

  final int UPDATE_PERIOD_MILLISECONDS = 10;
  private static final int WAIT_TIME_MILLISECONDS = 50;
  private static final int TEARDOWN_WAIT_TIME_MILLISECONDS = 500;
  FakeTimeGetter timeGetter;
  Thread masterThread;
  RenderThread renderThread;
  RenderThreadUpdateSpy renderThreadUpdateSpy = new RenderThreadUpdateSpy();

  @Before
  public void setUp() {
    timeGetter = new FakeTimeGetter(0);
    renderThread =
        new RenderThread(renderThreadUpdateSpy, UPDATE_PERIOD_MILLISECONDS, timeGetter);
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
    assertEquals(0, renderThreadUpdateSpy.getUpdateCalls());
    timeGetter.setCurrentTime(10);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(1, renderThreadUpdateSpy.getUpdateCalls());
    timeGetter.setCurrentTime(20);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(2, renderThreadUpdateSpy.getUpdateCalls());
  }

  @Test
  public void doNotCallUpdateBeforeUpdatePeriods() throws InterruptedException {
    assertEquals(0, renderThreadUpdateSpy.getUpdateCalls());
    timeGetter.setCurrentTime(9);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(0, renderThreadUpdateSpy.getUpdateCalls());
  }

}
