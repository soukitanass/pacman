/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.FakeTimeGetter;
import ca.usherbrooke.pacman.view.IGameView;

public class RenderThreadTest {

  final int FPS = 100;
  private static final int WAIT_TIME_MILLISECONDS = 50;
  private static final int TEARDOWN_WAIT_TIME_MILLISECONDS = 500;
  FakeTimeGetter timeGetter;
  Thread masterThread;
  RenderThread renderThread;
  IGameView viewUpdateSpy = mock(IGameView.class);

  @Before
  public void setUp() {
    timeGetter = new FakeTimeGetter(0);
    renderThread = new RenderThread(viewUpdateSpy, timeGetter);
    renderThread.setFps(FPS);
    masterThread = new Thread(renderThread);
    masterThread.start();
    try {
      masterThread.join(100);
    } catch (InterruptedException e) {
      masterThread.interrupt();
      e.printStackTrace();
    }
    assertTrue("thread dead", masterThread.isAlive());
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

  @Test
  public void fpsIsInitiallyZero() {
    assertEquals(0, renderThread.getFps());
  }

  @Test
  public void fpsIsAverageOfLast5UpdatesWhenPeriodIsConstant() throws InterruptedException {
    timeGetter.setCurrentTime(10);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(20);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(30);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(40);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(50);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(60);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());
  }

  @Test
  public void fpsIsAverageOfLast5UpdatesWhenPeriodIsVarying() throws InterruptedException {
    timeGetter.setCurrentTime(10);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(100, renderThread.getFps());

    timeGetter.setCurrentTime(40);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(50, renderThread.getFps());

    timeGetter.setCurrentTime(50);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(60, renderThread.getFps());

    timeGetter.setCurrentTime(80);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(50, renderThread.getFps());

    timeGetter.setCurrentTime(100);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(50, renderThread.getFps());

    timeGetter.setCurrentTime(135);
    Thread.sleep(WAIT_TIME_MILLISECONDS);
    assertEquals(40, renderThread.getFps());
  }

}
